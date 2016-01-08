using System;
using NUnit.Framework;
using Castle.Windsor.Configuration.Interpreters;
using NetBpm.Web;

namespace NetBpm.Test.Web
{
	/// <summary>
	/// The base class for all web Test cases. It initialises the container.
	/// </summary>
	public abstract class NetBpmWebTestCase
	{
		private NetBpmContainer _container;

		[SetUp]
		public void InitContainer()
		{
			_container = new NetBpmContainer( 
				new XmlInterpreter("../app_test_config.xml") );
		}

		[TearDown]
		public void DisposeContainer()
		{
			_container.Dispose();
			_container = null;
		}

		public NetBpmContainer Container
		{
			get { return _container; }
		}
	}
}
