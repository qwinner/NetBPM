using System;
using Nullables;
using NetBpm.Util.DB;
using NetBpm.Workflow.Definition;
using NetBpm.Workflow.Definition.Impl;
using NetBpm.Workflow.Organisation;

namespace NetBpm.Workflow.Execution.Impl
{
	public class ProcessInstanceImpl : PersistentObject, IProcessInstance
	{
		private NullableDateTime _start = NullableDateTime.Parse(null);
		private NullableDateTime _end = NullableDateTime.Parse(null);
		private bool active = true;
		private String _initiatorActorId = null;
		private IProcessDefinition _processDefinition = null;
		private IFlow _rootFlow = null;
		private IFlow _superProcessFlow = null;
		private static readonly OrganisationUtil organisationUtil = OrganisationUtil.Instance;

		public bool StartHasValue
		{
			get { return this._start.HasValue; }
		}

		public bool EndHasValue
		{
			get { return this._end.HasValue; }
		}

		public NullableDateTime EndNullable
		{
			get { return this._end; }
			set { this._end = value; }
		}

		public NullableDateTime StartNullable
		{
			get { return this._start; }
			set { this._start = value; }
		}

		public DateTime End
		{
			get { return this._end.Value; }
			set { this._end = new NullableDateTime(value); }
		}

		public DateTime Start
		{
			get { return this._start.Value; }
			set { this._start = new NullableDateTime(value); }
		}

		public String InitiatorActorId
		{
			get { return _initiatorActorId; }
			set { this._initiatorActorId = value; }
		}

		public IProcessDefinition ProcessDefinition
		{
			get { return this._processDefinition; }
			set { this._processDefinition = value; }
		}

		public IFlow RootFlow
		{
			get { return this._rootFlow; }
			set { this._rootFlow = value; }
		}

		public IFlow SuperProcessFlow
		{
			get { return this._superProcessFlow; }
			set { this._superProcessFlow = value; }
		}

		public bool Active
		{
			get { return active; }
		}

		public ProcessInstanceImpl()
		{
		}

		public ProcessInstanceImpl(String actorId, ProcessDefinitionImpl processDefinition)
		{
			this._start = DateTime.Now;
			this._initiatorActorId = actorId;
			this._processDefinition = processDefinition;
			this._rootFlow = new FlowImpl(actorId, this);
		}

		public IActor GetInitiator()
		{
			return organisationUtil.GetActor(_initiatorActorId);
		}

		public override String ToString()
		{
			return "processInstance[" + _id + "|" + _processDefinition.Name + "]";
		}
	}
}