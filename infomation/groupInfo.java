package infomation;

import java.util.ArrayList;

/**
 * 群组信息类
 */
public class groupInfo extends infoBase {

    /**
     * 构造
     * @param id 群组ID
     * @param name 群组名称 
     */
    public groupInfo(char[] id, String name) {
        super(id, name);
    }

    /**
     * @param id 新成员ID
     */
    public void addMember(char[] id){
        
    }
    private ArrayList<infoBase> members = new ArrayList<infoBase>();
}