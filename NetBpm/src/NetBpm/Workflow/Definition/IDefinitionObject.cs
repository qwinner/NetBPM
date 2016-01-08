using System;

namespace NetBpm.Workflow.Definition
{
	public interface IDefinitionObject //: System.Runtime.Serialization.ISerializable
	{
		Int64 Id { get; }
		String Name { get; }
		String Description { get; }
		IProcessDefinition ProcessDefinition { get; }
		bool HasName();
	}
}