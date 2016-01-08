using System;
using NetBpm.Util.Xml;
using NetBpm.Workflow.Delegation.Impl;

namespace NetBpm.Workflow.Definition.Impl
{
	public class JoinImpl : NodeImpl, IJoin
	{
		[NonSerialized()] private DelegationImpl _joinDelegation = null;

		/// <summary> overrides the standard scope, transitions of a join will arrive in the parent-processblock of the concurrent block.</summary>
		protected internal override CreationContext TransitionDestinationScope
		{
			set { value.TransitionDestinationScope = value.ProcessBlock.ParentBlock; }

		}

		public DelegationImpl JoinDelegation
		{
			get { return _joinDelegation; }
			set { this._joinDelegation = value; }
		}

		public JoinImpl()
		{
		}

		public JoinImpl(IProcessDefinition processDefinition) : base(processDefinition)
		{
		}

		public DelegationImpl CreateJoinDelegation()
		{
			_joinDelegation = new DelegationImpl(_processDefinition);
			return _joinDelegation;
		}

		public override void ReadProcessData(XmlElement xmlElement, CreationContext creationContext)
		{
			base.ReadProcessData(xmlElement, creationContext);

			if ((Object) xmlElement.GetAttribute("handler") != null)
			{
				creationContext.DelegatingObject = this;
				this._joinDelegation = new DelegationImpl();
				this._joinDelegation.ReadProcessData(xmlElement, creationContext);
				creationContext.DelegatingObject = null;
			}
		}

		public override void Validate(ValidationContext validationContext)
		{
			base.Validate(validationContext);

			// check this join has exactly one leaving transition
			validationContext.Check((_leavingTransitions.Count == 1), "the join has " + _leavingTransitions.Count + " leaving transition instead of exactly one");
		}
	}
}