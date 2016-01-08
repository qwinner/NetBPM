using System;
using System.Collections;
using Iesi.Collections;
using log4net;
using Nullables;
using NetBpm.Util.DB;
using NetBpm.Workflow.Definition;
using NetBpm.Workflow.Definition.Impl;
using NetBpm.Workflow.Log.Impl;
using NetBpm.Workflow.Organisation;

namespace NetBpm.Workflow.Execution.Impl
{
	public class FlowImpl : PersistentObject, IFlow
	{
		private String _name = null;
		private String _actorId = null;
		private NullableDateTime _start = NullableDateTime.Parse(null);
		private NullableDateTime _end = NullableDateTime.Parse(null);
		private bool active = true;
		private Boolean _parentReactivation;
		private INode _node = null;
		private IProcessInstance _processInstance = null;
		private ISet _subProcessInstances = null;
		private ISet _attributeInstances = null;
		private IList _userMessages = new ArrayList();
		private IFlow _parent = null;
		private ISet _children = null;
		private ISet _logs = null;

		private static readonly OrganisationUtil organisationUtil = OrganisationUtil.Instance;
		private static readonly ILog log = LogManager.GetLogger(typeof (FlowImpl));

		public String Name
		{
			get { return this._name; }
			set { this._name = value; }
		}

		public String ActorId
		{
			get { return _actorId; }
			set { this._actorId = value; }
		}

		public Boolean ParentReactivation
		{
			get { return _parentReactivation; }
			set { this._parentReactivation = value; }
		}

		public NullableDateTime StartNullable
		{
			get { return this._start; }
			set { this._start = value; }
		}

		public NullableDateTime EndNullable
		{
			get { return this._end; }
			set
			{
				this.active = false;
				this._end = value;
			}
		}

		public bool StartHasValue
		{
			get { return this._start.HasValue; }
		}
			
		public DateTime Start
		{
			get { return this._start.Value; }
			set { this._start = new NullableDateTime(value); }
		}

		public bool EndHasValue
		{
			get { return this._end.HasValue; }
		}

		public DateTime End
		{
			get { return this._end.Value; }
			set
			{
				this.active = false;
				this._end = new NullableDateTime(value);
			}
		}

		public INode Node
		{
			get { return this._node; }
			set { this._node = value; }
		}

		public IProcessInstance ProcessInstance
		{
			get { return this._processInstance; }
			set { this._processInstance = value; }
		}

		public ISet AttributeInstances
		{
			get { return this._attributeInstances; }
			set { this._attributeInstances = value; }
		}

		public IFlow Parent
		{
			get { return this._parent; }
			set { this._parent = value; }
		}

		public ISet Children
		{
			get { return this._children; }
			set { this._children = value; }
		}

		public ISet Logs
		{
			get { return this._logs; }
			set { this._logs = value; }
		}

		/// <summary> Hibernate only supports 1-1 relations if the objects have the same id.  Since that was not 
		/// easily achievable, I decided to make it an 1-n relation towards hibernate, but masked the 
		/// collections property private.  Then I added the property getter and setter which put or get 
		/// the single object from the collection.  
		/// </summary>
		/// <seealso cref="GetSubProcessInstance()">
		/// </seealso>
		/// <seealso cref="SetSubProcessInstance(IProcessInstance)">
		/// </seealso>
		private ISet SubProcessInstances
		{
			get { return _subProcessInstances; }
			set { this._subProcessInstances = value; }
		}

		public bool Active
		{
			get { return active; }
		}

		public FlowImpl()
		{
		}

		public FlowImpl(String actorId, ProcessInstanceImpl processInstance)
		{
			ProcessDefinitionImpl processDefinition = (ProcessDefinitionImpl) processInstance.ProcessDefinition;
			this._name = "root";
			this._actorId = actorId;
			this._start = DateTime.Now;
			this._node = processDefinition.StartState;
			this._processInstance = processInstance;
			this._subProcessInstances = null;
			this._parent = null;
			this._children = null;
			this._logs = new ListSet();
			this._parentReactivation = true;
			CreateAttributeInstances(processDefinition.Attributes);
		}

		public FlowImpl(String name, FlowImpl parent, ProcessBlockImpl processBlock)
		{
			this._name = name;
			this._start = DateTime.Now;
			this._processInstance = parent.ProcessInstance;
			this._subProcessInstances = null;
			this._parent = parent;
			this._children = null;
			this._logs = new ListSet();
			this._parentReactivation = true;
			CreateAttributeInstances(processBlock.Attributes);
		}

		public void CreateAttributeInstances(ISet attributes)
		{
			this._attributeInstances = new ListSet();
			IEnumerator iter = attributes.GetEnumerator();
			while (iter.MoveNext())
			{
				AttributeImpl attribute = (AttributeImpl) iter.Current;
				String attributeName = attribute.Name;
				log.Debug("creating the attribute " + attribute + " for flow " + this);
				AttributeInstanceImpl attributeInstance = new AttributeInstanceImpl(attribute, this);
				attributeInstance.ValueText = attribute.InitialValue;
				this._attributeInstances.Add(attributeInstance);
			}
		}

		public IList GetUserMessages()
		{
			return _userMessages;
		}

		public IActor GetActor()
		{
			return organisationUtil.GetActor(_actorId);
		}

		public LogImpl CreateLog(EventType eventType)
		{
			return CreateLog(null, eventType);
		}

		public LogImpl CreateLog(String actorId, EventType eventType)
		{
			LogImpl log = new LogImpl(actorId, DateTime.Now, eventType, this);
			if (_logs == null)
			{
				_logs = new ListSet();
			}
			_logs.Add(log);
			return log;
		}

		public IProcessInstance GetSubProcessInstance()
		{
			IProcessInstance subProcessInstance = null;
			if (_subProcessInstances != null)
			{
				if (_subProcessInstances.Count == 1)
				{
					subProcessInstance = (IProcessInstance) _subProcessInstances.GetEnumerator().Current;
				}
			}
			return subProcessInstance;
		}

		public void SetSubProcessInstance(IProcessInstance subProcessInstance)
		{
			_subProcessInstances = new ListSet();
			_subProcessInstances.Add(subProcessInstance);
		}

		public bool IsRootFlow()
		{
			return (_parent == null);
		}

		public override String ToString()
		{
			return "flow[" + _id + "|" + _name + "]";
		}
	}
}