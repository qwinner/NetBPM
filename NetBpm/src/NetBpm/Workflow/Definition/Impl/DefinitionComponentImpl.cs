using System;
using System.Collections;
using System.IO;
using ICSharpCode.SharpZipLib.Zip;
using log4net;
using NetBpm.Util.Client;
using NetBpm.Util.DB;
using NetBpm.Util.Xml;

namespace NetBpm.Workflow.Definition.Impl
{
	public class DefinitionComponentImpl
	{
		private static readonly DefinitionComponentImpl instance = new DefinitionComponentImpl();
		private static readonly ILog log = LogManager.GetLogger(typeof (DefinitionComponentImpl));

		/// <summary> gets the singleton instance.</summary>
		public static DefinitionComponentImpl Instance
		{
			get { return instance; }
		}

		private DefinitionComponentImpl()
		{
		}

		private const String queryFindProcessDefinitions = "select pd " + "from pd in class NetBpm.Workflow.Definition.Impl.ProcessDefinitionImpl " +
			"where pd.Version = ( " + "    select max(pd2.Version) " +
			"    from pd2 in class NetBpm.Workflow.Definition.Impl.ProcessDefinitionImpl " +
			"    where pd2.Name = pd.Name )";

		public IList GetProcessDefinitions(Relations relations, DbSession dbSession)
		{
			IList processDefinitions = null;
			processDefinitions = dbSession.Find(queryFindProcessDefinitions);
			if (relations != null)
			{
				relations.Resolve(processDefinitions);
			}
			return processDefinitions;
		}

		private const String queryFindProcessDefinitionByName = "select pd " +
			"from pd in class NetBpm.Workflow.Definition.Impl.ProcessDefinitionImpl " +
			"where pd.Name = ? " + "  and pd.Version = ( " + "    select max(pd2.Version) " +
			"    from pd2 in class NetBpm.Workflow.Definition.Impl.ProcessDefinitionImpl " +
			"    where pd2.Name = pd.Name )";

		public IProcessDefinition GetProcessDefinition(String processDefinitionName, Relations relations, DbSession dbSession)
		{
			ProcessDefinitionImpl processDefinition = null;
			processDefinition = (ProcessDefinitionImpl) dbSession.FindOne(queryFindProcessDefinitionByName, processDefinitionName, DbType.STRING);
			if (relations != null)
			{
				relations.Resolve(processDefinition);
			}
			return processDefinition;
		}

		public IProcessDefinition GetProcessDefinition(Int64 processDefinitionId, Relations relations, DbSession dbSession)
		{
			ProcessDefinitionImpl processDefinition = null;
			processDefinition = (ProcessDefinitionImpl) dbSession.Load(typeof (ProcessDefinitionImpl), processDefinitionId);
			if (relations != null)
			{
				relations.Resolve(processDefinition);
			}
			return processDefinition;
		}

		private const String queryFindAllProcessDefinitions = "from pd in class NetBpm.Workflow.Definition.Impl.ProcessDefinitionImpl";

		public IList GetAllProcessDefinitions(Relations relations, DbSession dbSession)
		{
			IList processDefinitions = null;
			log.Debug("getting all process definitions...");
			processDefinitions = dbSession.Find(queryFindAllProcessDefinitions);
			if (relations != null)
			{
				relations.Resolve(processDefinitions);
			}
			return processDefinitions;
		}

		public void DeployProcessArchive(byte[] processArchiveBytes, DbSession dbSession)
		{
			try
			{
				Stream mstream = new MemoryStream(processArchiveBytes);
				DeployProcessArchive(mstream, dbSession);
			}
			catch (IOException e)
			{
				throw new NpdlException("couldn't deploy process archive, the processArchiveBytes do not seem to be a valid par-file : " + e.Message, e);
			}
		}

		public void DeployProcessArchive(Stream processArchiveStream, DbSession dbSession)
		{
			log.Debug("reading process archive...");
			try
			{
				IDictionary entries = null;
				// construct an empty process definition
				ProcessDefinitionImpl processDefinition = new ProcessDefinitionImpl();
				try
				{
					entries = ReadEntries(processArchiveStream);
				}
				catch (IOException e)
				{
					throw new NpdlException("couldn't deploy process archive, the processArchiveBytes do not seem to be a valid jar-file : " + e.Message, e);
				}

				// Then save the process definition
				// This is done so hibernate will assign an id to this object.
				dbSession.Save(processDefinition);
				CreationContext creationContext = new CreationContext(processDefinition, entries, dbSession);
				try
				{
					// parse the  processdefinition.xml
					XmlElement xmlElement = GetXmlElementForEntry("processdefinition.xml", entries, creationContext);
					// build the object model from the xml
					creationContext.PushScope("in processdefinition.xml");
					processDefinition.ReadProcessData(xmlElement, creationContext);
					creationContext.PopScope();
					// resolve all forward references
					creationContext.ResolveReferences();

					processDefinition.Validate(creationContext);

					if (creationContext.HasErrors())
					{
						throw new NpdlException(creationContext.Errors);
					}
					// read the optional web-interface information
					if (entries.Contains("web/webinterface.xml"))
					{
						log.Debug("processing webinterface.xml...");
						xmlElement = GetXmlElementForEntry("web/webinterface.xml", entries, creationContext);
						creationContext.PushScope("in webinterface.xml");
						processDefinition.ReadWebData(xmlElement, creationContext);
						creationContext.PopScope();
					}
					else
					{
						log.Debug("no web/webinterface.xml was supplied");
					}

				}
				catch (SystemException e)
				{
					log.Error("xml parsing error :", e);
					creationContext.AddError(e.GetType().FullName + " : " + e.Message);
					creationContext.AddError("couldn't continue to parse the process archive");
					throw new NpdlException(creationContext.Errors);
				}

				// flush the changes to the database
				dbSession.SaveOrUpdate(processDefinition);
				dbSession.Flush();
			}
			catch (DbException e)
			{
				throw new NpdlException("couldn't deploy process archive due to a database exception : " + e.Message, e);
			}
		}

		/// <summary> reads the zipFile-InputStream and puts all entries in a Map, for which
		/// the keys are the path-names.
		/// </summary>
		/// <returns> a Map with the entry-path-names as keys and the byte-arrays as the contents.
		/// </returns>
		private IDictionary ReadEntries(Stream processArchiveStream)
		{
			IDictionary entries = new Hashtable();
			ZipInputStream s = new ZipInputStream(processArchiveStream);
			ZipEntry entry;
			// extract the file or directory entry
			while ((entry = s.GetNextEntry()) != null)
			{
				if (!entry.IsDirectory)
				{
					byte[] content = ZipStreamToByte(s);
					entries.Add(entry.Name, content);
				}
			}

			s.Close();
			return entries;
		}

		private XmlElement GetXmlElementForEntry(String entryName, IDictionary entries, CreationContext creationContext)
		{
			XmlElement xmlElement = null;

			byte[] processDefinitionXml = (byte[]) entries[entryName];
			if (processDefinitionXml == null)
			{
				creationContext.AddError("entry '" + entryName + "' found not found in the process archive");
				throw new SystemException("entry '" + entryName + "' found not found in the process archive");
			}

			Stream processDefinitionInputStream = new MemoryStream(processDefinitionXml);
			XmlParser xmlParser = new XmlParser(processDefinitionInputStream);
			xmlElement = xmlParser.Parse();
			return xmlElement;
		}

		private byte[] ZipStreamToByte(ZipInputStream s)
		{
			MemoryStream ms = new MemoryStream();
			BinaryWriter bw = new BinaryWriter(ms);

			while (true)
			{
				int size = 2048;
				byte[] data = new byte[2048];

				size = s.Read(data, 0, data.Length);
				if (size > 0)
				{
					bw.Write(data, 0, size);
				}
				else
				{
					break;
				}
			}
			bw.Flush();
			// deletes leading bytes in memStream
			ms.SetLength(ms.Position);
			ms.Seek(0, SeekOrigin.Begin);

			byte[] content = new Byte[ms.Length];
			ms.Read(content, 0, (int) ms.Length);
			return content;
		}
	}
}