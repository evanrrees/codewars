val s = "You }}need{{ >extra< time ) or money ("
fun f(s:String)=s.fold(""){r,c->r+"{(<>)}".run{getOrElse(reversed().indexOf(c)){c}}}
fun g(s:String)=s.fold(""){r,c->r+"{(<>)}".run{getOrNull(reversed().indexOf(c))?:c}}
// combine solution from 'Unnamed'
fun h(s:String)=s.fold(""){r,c->r+"$c()<>{}"[")(><}{".indexOf(c)+1]}
