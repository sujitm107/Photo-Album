package Model;

import java.io.Serializable;
/**
 * By Sujit Molleti and Rachana Kotamraju
 */

public class TagNode implements Serializable {

    /**
     * Each tag node has a tag and a value
     * The value is the tag type and the tag is the actual tag
     */
    private String tag;
    private String value;
    static final long serialVersionUID = 1L;

    /**
     * Constructor for tag node
     * @param tag
     * @param value
     */
    public TagNode(String tag, String value){
        this.tag = tag;
        this.value = value;
    }

    /**
     *
     * @return tag
     */
    public String getTag(){
        return tag;
    }

    /**
     *
     * @return tag type
     */

    public String getValue(){
        return value;
    }

    /**
     * Implements an equals method to compare two TagNodes
     * @param o
     * @return
     */
    public boolean equals(Object o){

        if( o == null ){
            return false;
        }

        TagNode other = (TagNode)o;

        return this.tag.equals(other.tag) && this.value.equals(other.value);
    }

}
