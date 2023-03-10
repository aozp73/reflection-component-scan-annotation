package reflect.aa.aab;

import reflect.Controller;
import reflect.RequestMapping;

@Controller
public class BoardController {

    @RequestMapping(uri = "/save")
    public void save() {
        System.out.println("save() 호출됨");
    }
}
