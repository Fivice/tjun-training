package tjuninfo.training.task.constant;

import com.alibaba.fastjson.JSONObject;
import netscape.javascript.JSObject;

import java.util.ArrayList;
import java.util.List;

public enum  NationCode{
    HAN(1, "汉"),
    ZHUANG(2, "壮"),
    MANCHU(3, "满"),
    HUI(4, "回"),
    MIAO(5, "苗"),
    UYGHUR(6, "维吾尔"),
    YI(7, "彝"),
    TUJIA(8, "土家"),
    MONGOL(9,"蒙古"),
    TIBETAN(10, "藏"),
    BUYEI(11, "布依"),
    DONG(12, "侗"),
    YAO(13, "瑶"),
    KOREAN(14, "朝鲜"),
    BAI(15, "白"),
    HANI(16, "哈尼"),
    LI(17, "黎"),
    KAZAK(18, "哈萨克"),
    DAI(19, "傣"),
    SHE(20, "畲"),
    LISU(21, "僳僳"),
    GELAO(22, "仡佬"),
    LAHU(23, "拉祜"),
    DONGXIANG(24,"东乡"),
    VA(25, "佤"),
    SUI(26, "水"),
    NAXI(27, "纳西"),
    QIANG(28, "羌"),
    TU(29, "土"),
    XIBE(30, "锡伯"),
    MULAO(31, "仫佬"),
    KIRGIZ(32, "柯尔克孜"),
    DAUR(33, "达斡尔"),
    JINGPO(34, "景颇"),
    SALAR(35, "撒拉"),
    BLANG(36, "布朗"),
    MAONAN(37, "毛南"),
    TAJIK(38, "塔吉克"),
    PUMI(39, "普米"),
    ACHANG(40, "阿昌"),
    NU(41, "怒"),
    EWENKI(42, "鄂温克"),
    GIN(43, "京"),
    JINO(44, "基诺"),
    DEANG(45, "德昂"),
    UZBEK(46,"乌孜别克"),
    RUSSIANS(47, "俄罗斯"),
    YUGUR (48, "裕固"),
    BONAN(49, "保安"),
    MONBA(50, "门巴"),
    OROQEN(51, "鄂伦春"),
    DERUNG(52, "独龙"),
    TATAR(53, "塔塔尔"),
    HEZHEN(54, "赫哲"),
    LHOBA(55, "珞巴"),
    GAOSHAN(56, "高山");

    /** 返回状态码 */
    private Integer value;

    /** 返回消息 */
    private String nation;

    private NationCode(int value, String nation) {
        this.value = value;
        this.nation = nation;
    }

    public Integer getValue(){
        return value;
    }

    public String getNation(){
        return nation;
    }

//    public static void main(String[] args) {
//        class Nation{
//            int id;
//            String name;
//
//            public Nation(int id, String name) {
//                this.id = id;
//                this.name = name;
//            }
//        }
//        List<Nation> list = new ArrayList<>();
//        for (NationCode n: NationCode.values()
//             ) {
//            Nation nation = new Nation(n.getValue(),n.getNation());
//            list.add(nation);
//        }
//        System.out.println(list.size());
//    }
}
