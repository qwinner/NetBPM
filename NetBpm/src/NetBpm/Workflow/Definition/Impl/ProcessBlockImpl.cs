using System.Collections;
using Iesi.Collections;
using NetBpm.Util.Xml;

namespace NetBpm.Workflow.Definition.Impl
{
	public class ProcessBlockImpl : DefinitionObjectImpl, IProcessBlock
	{
		protected internal ISet _nodes = null;
		protected internal ISet _attributes = null;
		protected internal ProcessBlockImpl _parentBlock = null;
		protected internal ISet _childBlocks = null;

		public ProcessBlockImpl ParentBlock
		{
			get { return this._parentBlock; }
			set { this._parentBlock = value; }
		}

		public ISet ChildBlocks
		{
			get { return this._childBlocks; }
			set { this._childBlocks = value; }
		}

		public ISet Nodes
		{
			get { return this._nodes; }
			set { this._nodes = value; }
		}

		public ISet Attributes
		{
			get { return this._attributes; }
			set { this._attributes = value; }
		}

		public ProcessBlockImpl()
		{
		}

		public ProcessBlockImpl(IProcessDefinition processDefinition) : base(processDefinition)
		{
			this._nodes = new ListSet();
			this._attributes = new ListSet();
			this._childBlocks = new ListSet();
		}

		public void AddNode(NodeImpl node)
		{
			_nodes.Add(node);
			node.ProcessBlock = this;
		}

		public AttributeImpl CreateAttribute()
		{
			AttributeImpl attribute = new AttributeImpl(_processDefinition);
			attribute.Scope = this;
			_attributes.Add(attribute);
			return attribute;
		}

		public ActivityStateImpl CreateActivityState()
		{
			ActivityStateImpl activityState = new ActivityStateImpl(_processDefinition);
			AddNode(activityState);
			return activityState;
		}

		public ProcessStateImpl CreateProcessState()
		{
			ProcessStateImpl processState = new ProcessStateImpl(_processDefinition);
			AddNode(processState);
			return processState;
		}

		public DecisionImpl CreateDecision()
		{
			DecisionImpl decision = new DecisionImpl(_processDefinition);
			AddNode(decision);
			return decision;
		}

		public ConcurrentBlockImpl CreateConcurrentBlock()
		{
			ConcurrentBlockImpl concurrentBlock = new ConcurrentBlockImpl(_processDefinition);
			concurrentBlock.ParentBlock = this;
			_childBlocks.Add(concurrentBlock);
			return concurrentBlock;
		}

		public override void ReadProcessData(XmlElement xmlElement, CreationContext creationContext)
		{
			this._nodes = new ListSet();
			this._attributes = new ListSet();
			this._childBlocks = new ListSet();

			base.ReadProcessData(xmlElement, creationContext);

			IEnumerator iter = xmlElement.GetChildElements("attribute").GetEnumerator();
			while (iter.MoveNext())
			{
				AttributeImpl attribute = new AttributeImpl();
				attribute.ReadProcessData((XmlElement) iter.Current, creationContext);
				_attributes.Add(attribute);
			}

			iter = xmlElement.GetChildElements("activity-state").GetEnumerator();
			while (iter.MoveNext())
			{
				ActivityStateImpl activityState = new ActivityStateImpl();
				activityState.ReadProcessData((XmlElement) iter.Current, creationContext);
				_nodes.Add(activityState);
			}

			iter = xmlElement.GetChildElements("process-state").GetEnumerator();
			while (iter.MoveNext())
			{
				ProcessStateImpl processState = new ProcessStateImpl();
				processState.ReadProcessData((XmlElement) iter.Current, creationContext);
				_nodes.Add(processState);
			}

			iter = xmlElement.GetChildElements("decision").GetEnumerator();
			while (iter.MoveNext())
			{
				DecisionImpl decision = new DecisionImpl();
				decision.ReadProcessData((XmlElement) iter.Current, creationContext);
				_nodes.Add(decision);
			}

			iter = xmlElement.GetChildElements("concurrent-block").GetEnumerator();
			while (iter.MoveNext())
			{
				ConcurrentBlockImpl concurrentBlock = new ConcurrentBlockImpl();
				concurrentBlock.ReadProcessData((XmlElement) iter.Current, creationContext);
				_childBlocks.Add(concurrentBlock);
			}
		}

		public override void Validate(ValidationContext validationContext)
		{
			base.Validate(validationContext);

			// validate the attributes
			IEnumerator iter = _attributes.GetEnumerator();
			while (iter.MoveNext())
			{
				AttributeImpl attribute = (AttributeImpl) iter.Current;
				attribute.Validate(validationContext);
			}

			// validate the nodes
			iter = _nodes.GetEnumerator();
			while (iter.MoveNext())
			{
				NodeImpl node = (NodeImpl) iter.Current;
				node.Validate(validationContext);
			}

			// validate the childBlocks
			iter = _childBlocks.GetEnumerator();
			while (iter.MoveNext())
			{
				ProcessBlockImpl processBlock = (ProcessBlockImpl) iter.Current;
				processBlock.Validate(validationContext);
			}
		}
	}
}