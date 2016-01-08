using System;
using NetBpm.Util.Client;
using NetBpm.Workflow.Definition.EComp;
using NetBpm.Workflow.Execution.EComp;
using NetBpm.Workflow.Organisation.EComp;
using NetBpm.Workflow.Scheduler.EComp;
using Castle.Windsor.Configuration.Interpreters;
using NUnit.Framework;

namespace NetBpm.Test.Workflow.Example
{
	// TODO refacture AbstractEcample Testcase and this class

	public abstract class AbstractTest
	{
		protected internal ServiceLocator servicelocator = null;
		protected internal IDefinitionSessionLocal definitionComponent = null;
		protected internal IExecutionSessionLocal executionComponent = null;
		protected internal ISchedulerSessionLocal schedulerComponent = null;
		protected internal IOrganisationSessionLocal organisationComponent = null;
		protected internal TestUtil testUtil = null;
		private NetBpmContainer _container = null;

//		[TestFixtureSetUp]
		[SetUp]
		public void SetUp()
		{
			SetContainer();
		}

//		[TestFixtureTearDown]
		[TearDown]
		public void TearDown()
		{
			DisposeContainer();
		}

		public void SetContainer()
		{
			//configure the container
			_container = new NetBpmContainer(new XmlInterpreter(TestHelper.GetConfigDir()+"app_config.xml"));
			testUtil = new TestUtil();
			servicelocator = ServiceLocator.Instance;
			definitionComponent = servicelocator.GetService(typeof (IDefinitionSessionLocal)) as IDefinitionSessionLocal;
			executionComponent = servicelocator.GetService(typeof (IExecutionSessionLocal)) as IExecutionSessionLocal;
			schedulerComponent = servicelocator.GetService(typeof (ISchedulerSessionLocal)) as ISchedulerSessionLocal;
			organisationComponent = servicelocator.GetService(typeof (IOrganisationSessionLocal)) as IOrganisationSessionLocal;
			testUtil.LoginUser("ae");
		}

		public void DisposeContainer()
		{	
			servicelocator.Release(definitionComponent);
			definitionComponent=null;
			servicelocator.Release(executionComponent);
			executionComponent=null;
			servicelocator.Release(schedulerComponent);
			schedulerComponent=null;
			servicelocator.Release(organisationComponent);
			organisationComponent=null;

			_container.Dispose();
			_container = null;
		}
	}
}
