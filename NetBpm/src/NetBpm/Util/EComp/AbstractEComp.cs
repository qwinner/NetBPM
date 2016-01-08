using NetBpm.Util.DB;
using NHibernate;

using Castle.Facilities.NHibernateIntegration;

namespace NetBpm.Util.EComp
{
	/// <summary>
	/// </summary>
	public class AbstractEComp //: MarshalByRefObject
	{
		public readonly ISessionManager sessionManager;

		public AbstractEComp(ISessionManager sessionManager)
		{
			this.sessionManager = sessionManager;
		}

		protected DbSession OpenSession()
		{
			ISession session = sessionManager.OpenSession();
			return new DbSession(session);
		}
	}
}