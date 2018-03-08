package com.juliar.nodes;

import com.juliar.errors.JuliarLogger;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Created by donreamey on 10/28/16.
 */
@SuppressWarnings("serial")
public class FinalNode extends NodeImpl{
    private String dataString;
    private transient Object object;
    private NodeType nodeType = NodeType.FinalType;
    private byte[] instructionBytes;

    public FinalNode(TerminalNode data){
        instructionBytes = data.getText().getBytes();
        if (data.getText() != null ) {
            dataString = data.getText();
        } else if (data.getSymbol().getText() != null ){
            dataString = data.getSymbol().getText();
        }
    }

    public FinalNode(){
    }

    public void setDataString(Object data){
        object = data;
    }

    public byte[] getFinalNodeBytes(){
        return instructionBytes;
    }

    public <T> T getBytesAstype(Class<T> asType){
       return asType.cast( instructionBytes );
    }

    public String dataString() {
        if ( object == null && dataString == null){
            return "";
        }

        if ( object != null && dataString == null){
            return object.toString();
        }

        return dataString;

    }

    public Object dataObject(){
        if (object != null){
            return object;
        }

        return null;
    }

    @Override
    public IntegralType getIntegralType() {
        if (dataString.startsWith("\"") && dataString.endsWith("\"")) {
            return IntegralType.jstring;
        }

        try {
            if (dataString.equalsIgnoreCase("true") || dataString.toLowerCase().endsWith("false")) {
                return IntegralType.jboolean;
            }
        } catch (Exception e) {
            JuliarLogger.log(e);
        }
        try {
            return IntegralType.jinteger;
        } catch (Exception e) {
            JuliarLogger.log(e);
        }

        try {
            return IntegralType.jdouble;
        } catch (Exception e) {
            JuliarLogger.log(e);
        }

        try {
            return IntegralType.jfloat;
        } catch (Exception e) {
            JuliarLogger.log(e);
        }

        try {
            return IntegralType.jlong;
        } catch (Exception e) {
            JuliarLogger.log(e);
        }

        return IntegralType.jobject;
    }

    @Override
    public NodeType getType() {
        dataString();
        if ( dataString == null ) {
            return NodeType.FinalType;
        }

        switch (dataString) {
            case "==":
                nodeType = NodeType.EqualEqualType;
                break;
            case "function":
                nodeType = NodeType.FunctionDeclType;
                break;
            case "<=":
            case "=>":
            case "<":
            case ">":
            default:
                assert false : "Type not implemented";
        }

        return nodeType;
    }

}
