using System;
using Iesi.Collections;

namespace NetBpm.Workflow.Organisation.Impl
{
	public class UserImpl : ActorImpl, IUser
	{
		// private members
		private String _firstName = null;
		private String _lastName = null;
		private String _email = null;
		private ISet _memberships = null;

		public string FirstName
		{
			set { _firstName = value; }
			get { return _firstName; }
		}

		public string LastName
		{
			set { _lastName = value; }
			get { return _lastName; }
		}

		public string Email
		{
			set { _email = value; }
			get { return _email; }
		}

		public ISet Memberships
		{
			set { _memberships = value; }
			get { return _memberships; }
		}

		public override string Name
		{
			get { return _firstName + " " + _lastName; }
			set
			{
			}
		}
	}
}