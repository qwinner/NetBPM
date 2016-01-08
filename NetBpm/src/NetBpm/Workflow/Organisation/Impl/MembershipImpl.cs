using System;

namespace NetBpm.Workflow.Organisation.Impl
{
	public class MembershipImpl : IMembership
	{
		// private members
		private Int64 _id = 0;
		private String _role = null;
		private String _type = null;
		private IGroup _group = null;
		private IUser _user = null;

		public Int64 Id
		{
			// persistent properties
			get { return _id; }
			set { this._id = value; }
		}

		public String Role
		{
			set { _role = value; }
			get { return _role; }
		}

		public String Type
		{
			set { _type = value; }
			get { return _type; }
		}

		public IGroup Group
		{
			set { _group = value; }
			get { return _group; }
		}

		public IUser User
		{
			set { _user = value; }
			get { return _user; }
		}

		public override String ToString()
		{
			return "Membership[" + _id + "|" + _user.Name + "|" + _group.Name + "]";
		}
	}
}