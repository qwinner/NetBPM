using System;
using NUnit.Framework;
using Castle.MonoRail.TestSupport;

namespace NetBpm.Test.Web
{
	/// <summary>
	/// Test the behaviour of the web application by using the holiday example.
	/// </summary>
	[TestFixture]
	public class HolidayTestCase : AbstractMRTestCase
	{

		[Test]
		[Ignore("ignoring this test method for now")]
		public void SimpleControllerAction()
		{
			DoGet("intro/index.rails");
			AssertSuccess();
		}
	}
}
