using System;
using System.Collections;

namespace NetBpm.Workflow.Organisation.Impl
{
	public class GroupImpl : ActorImpl, IGroup
	{
		// private members
		private String _name = null;
		private String _type = null;
		private ICollection _memberships = null;
		private IGroup _parent = null;
		private ICollection _children = null;

		public String Type
		{
			get { return _type; }
			set { this._type = value; }
		}

		public override string Name
		{
			set { _name = value; }
			get { return _name; }
		}

		public ICollection Memberships
		{
			set { _memberships = value; }
			get { return _memberships; }
		}

		public IGroup Parent
		{
			set { _parent = value; }
			get { return _parent; }
		}

		public ICollection Children
		{
			set { _children = value; }
			get { return _children; }
		}
	}
}