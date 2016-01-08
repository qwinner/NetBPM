using Castle.Facilities.NHibernateIntegration;
using Castle.Facilities.AutomaticTransactionManagement;

using NHibernate;

using NUnit.Framework;
using Castle.Windsor;


namespace NetBpm.Test.Workflow
{
	[TestFixture]
	public class DBTest
	{
		protected IWindsorContainer container;

		[SetUp]
		public void Init()
		{
			container = new WindsorContainer( TestHelper.GetConfigDir()+"WindsorContainerTest.xml" );
			container.AddFacility( "transactions", new TransactionFacility() );
		}

		[Test]
		public void SimpleGetSession()
		{
			ISessionManager sessionManager = (ISessionManager) 
				container[ typeof(ISessionManager) ];
			Assert.IsNotNull(sessionManager);
			ISession session =  sessionManager.OpenSession();
			Assert.IsNotNull(session);
			Assert.IsNull(session.Transaction);
			session.Close();
		}

		[TearDown]
		public void Dispose()
		{
			container.Dispose();
			container = null;
		}
	}
}
