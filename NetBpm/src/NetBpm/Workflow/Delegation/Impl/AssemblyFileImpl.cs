using System;
using NetBpm.Workflow.Definition;

namespace NetBpm.Workflow.Delegation.Impl
{
	public class AssemblyFileImpl
	{
		private Int64 id;
		private String fileName = null;
		private String assemblyName = null;
		private String assemblyVersion = null;
		private byte[] bytes = null;
		private IProcessDefinition processDefinition = null;

		public Int64 Id
		{
			get { return id; }
			set { this.id = value; }
		}

		public String FileName
		{
			get { return fileName; }
			set { this.fileName = value; }
		}

		public byte[] Bytes
		{
			get { return bytes; }
			set { this.bytes = value; }
		}

		public IProcessDefinition ProcessDefinition
		{
			get { return processDefinition; }
			set { this.processDefinition = value; }
		}

		public string AssemblyName
		{
			get { return assemblyName; }
			set { assemblyName = value; }
		}

		public string AssemblyVersion
		{
			get { return assemblyVersion; }
			set { assemblyVersion = value; }
		}

		public override String ToString()
		{
			return "classFile[" + id + "|" + fileName + "|" + processDefinition.Id + "]";
		}
	}
}