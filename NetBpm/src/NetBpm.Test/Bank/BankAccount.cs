using System;

namespace NetBpm.Test.Bank 
{
	public class BankAccount
	{	
		private float accountValue;
		private string bankName;
		private string customer;
		private Int64 id;

		public Int64 Id
		{
			get { return id; }
			set { this.id = value; }
		}

		public float Value
		{
			get { return this.accountValue; }
			set { this.accountValue = value; }
		}

		public string BankName
		{
			get { return this.bankName; }
			set { this.bankName = value; }
		}

		public string Customer
		{
			get { return this.customer; }
			set { this.customer = value; }
		}
	}
}
