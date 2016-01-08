using System;
using System.Collections;
using log4net;
using NetBpm.Util.DB;
using NetBpm.Util.Xml;

namespace NetBpm.Workflow.Definition.Impl
{
	public class DefinitionObjectImpl : PersistentObject, IDefinitionObject
	{
		private static readonly ILog log = LogManager.GetLogger(typeof (DefinitionObjectImpl));
		protected internal String _name = null;
		protected internal String _description = null;
		protected internal IList _actions = null;
		protected internal IProcessDefinition _processDefinition = null;

		public IList Actions
		{
			get { return _actions; }
			set { this._actions = value; }
		}

		public string Name
		{
			set { _name = value; }
			get { return _name; }
		}

		public string Description
		{
			set { _description = value; }
			get { return _description; }
		}

		public IProcessDefinition ProcessDefinition
		{
			set { _processDefinition = value; }
			get { return _processDefinition; }
		}

		public String TypeName
		{
			get
			{
				String className = this.GetType().FullName;
				int to = className.Length;
				if (className.EndsWith("Impl"))
				{
					to = to - 4;
				}
				int from = className.LastIndexOf((Char) '.') + 1;
				return className.Substring(from, (to) - (from));
			}

		}

		public DefinitionObjectImpl()
		{
		}

		public DefinitionObjectImpl(IProcessDefinition processDefinition)
		{
			this._processDefinition = processDefinition;
			this._actions = new ArrayList();
		}

		public ActionImpl CreateAction()
		{
			ActionImpl action = new ActionImpl();
			_actions.Add(action);
			// The relation between DefinitionObjectImpl and Action is 
			// not modelled in hibernate because the hibernate-feature
			// of mixing subclass with joined-subclass was still under 
			// construction.
			// On the other hand, we can't set the id-reference in the
			// action because at parsing-time, this DefinitionObjectImpl
			// does not yet have an id.  @see action.setDefinitionObjectId( id );
			return action;
		}

		public virtual void ReadProcessData(XmlElement xmlElement, CreationContext creationContext)
		{
			this._name = xmlElement.GetProperty("name");
			log.Debug("parsing '" + xmlElement.Name + "' with name '" + _name + "'");
			this._description = xmlElement.GetProperty("description");
			this._processDefinition = creationContext.ProcessDefinition;
			IEnumerator iter = xmlElement.GetChildElements("action").GetEnumerator();
			while (iter.MoveNext())
			{
				creationContext.DefinitionObject = this;
				XmlElement actionElement = (XmlElement) iter.Current;
				ActionImpl action = new ActionImpl();
				action.ReadProcessData(actionElement, creationContext);
				creationContext.DefinitionObject = null;
			}
		}

		public virtual void Validate(ValidationContext validationContext)
		{
		}

		public bool HasName()
		{
			return ((Object) this._name != null);
		}

		public override String ToString()
		{
			return TypeName + "[" + _id + "|" + _name + "]";
		}
	}
}