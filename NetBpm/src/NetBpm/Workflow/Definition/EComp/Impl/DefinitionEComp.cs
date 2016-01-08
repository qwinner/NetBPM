using System;
using System.Collections;
using NetBpm.Util.Client;
using NetBpm.Util.DB;
using NetBpm.Util.EComp;
using NetBpm.Workflow.Definition.Impl;
using Castle.Services.Transaction;
using Castle.Facilities.NHibernateIntegration;

namespace NetBpm.Workflow.Definition.EComp.Impl
{
	/// <summary> reads the process-definition-jar-file (= process archive) and
	/// stores all content in the database. 
	/// </summary>
	[Transactional]
	public class DefinitionEComp : AbstractEComp, IDefinitionSessionLocal
	{
		private static readonly DefinitionComponentImpl implementation = DefinitionComponentImpl.Instance;

		public DefinitionEComp(ISessionManager sessionManager) : base(sessionManager)
		{
		}

		/// <summary> this method is exposed only by the remote-interface and not the local-interface.</summary>
		[Transaction(TransactionMode.Requires)]
		public virtual void DeployProcessArchive(byte[] processArchiveBytes)
		{
			DbSession dbSession = null;
			try
			{
				dbSession = OpenSession();
				implementation.DeployProcessArchive(processArchiveBytes, dbSession);
			}
			catch (NpdlException e)
			{
				throw e;
			}
			catch (Exception e)
			{
				throw new SystemException("uncaught exception : " + e.Message, e);
			}
		}


		[Transaction(TransactionMode.Requires)]
		public virtual IList GetProcessDefinitions()
		{
			return GetProcessDefinitions(null);
		}

		[Transaction(TransactionMode.Requires)]
		public virtual IList GetProcessDefinitions(Relations relations)
		{
			IList processDefinitions = null;
			DbSession dbSession = null;
			dbSession = OpenSession();
			processDefinitions = implementation.GetProcessDefinitions(relations, dbSession);
			return processDefinitions;
		}

		[Transaction(TransactionMode.Requires)]
		public virtual IProcessDefinition GetProcessDefinition(String processDefinitionName)
		{
			return GetProcessDefinition(processDefinitionName, null);
		}

		[Transaction(TransactionMode.Requires)]
		public virtual IProcessDefinition GetProcessDefinition(String processDefinitionName, Relations relations)
		{
			IProcessDefinition processDefinition = null;
			DbSession dbSession = null;
			dbSession = OpenSession();
			processDefinition = implementation.GetProcessDefinition(processDefinitionName, relations, dbSession);
			return processDefinition;
		}

		[Transaction(TransactionMode.Requires)]
		public virtual IProcessDefinition GetProcessDefinition(Int64 processDefinitionId)
		{
			return GetProcessDefinition(processDefinitionId, null);
		}

		[Transaction(TransactionMode.Requires)]
		public virtual IProcessDefinition GetProcessDefinition(Int64 processDefinitionId, Relations relations)
		{
			IProcessDefinition processDefinition = null;
			DbSession dbSession = null;
			dbSession = OpenSession();
			processDefinition = implementation.GetProcessDefinition(processDefinitionId, relations, dbSession);
			return processDefinition;
		}


		[Transaction(TransactionMode.Requires)]
		public virtual IList GetAllProcessDefinitions()
		{
			return GetAllProcessDefinitions(null);
		}

		[Transaction(TransactionMode.Requires)]
		public virtual IList GetAllProcessDefinitions(Relations relations)
		{
			IList allProcessDefinitions = null;
			DbSession dbSession = null;
			dbSession = OpenSession();
			allProcessDefinitions = implementation.GetAllProcessDefinitions(relations, dbSession);
			return allProcessDefinitions;
		}
	}
}