package webhook

import webhook.enums.Options

class ListenerCollection(val listener: Any, val authorization: String, val options: Array<Options>)