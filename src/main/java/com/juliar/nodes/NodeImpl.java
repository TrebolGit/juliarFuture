package com.juliar.nodes;

import com.juliar.interpreter.ActivationFrame;
import com.juliar.interpreter.Interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.io.*;

import static com.juliar.nodes.IntegralType.*;

/**
 * Created by Don on 1/13/2017.
 */
@SuppressWarnings("serial")
public abstract class NodeImpl implements Node , Serializable{
    protected transient List<Node> instructions = new ArrayList<>();
    public Interpreter interpreter;
    public ActivationFrame frame;

    private IntegralType integralType;


    public NodeImpl(){
        //setNodeName();
    }

    @Override
    public void addInst(Node parent, Node instruction) {
    /*add instruction given parent*/
    }

    @Override
    public void addInst(Node instruction) {
        instructions.add( instruction );
    }

    @Override
    public void addInst(Stack<Node> contextStack, Node instruction) {
        Node n = contextStack.peek();
        n.addInst(instruction);
    }

    @Override
    public List<Node> getInstructions(){
        return instructions;
    }

    @Override
    public IntegralType getIntegralType(){
        return integralType;
    }

    @Override
    public void setVariableTypeByIntegralType( IntegralType type){
        integralType = type;
    }

    @Override
    public void setVariableType(String variableType){
        switch (variableType) {
            case "int":
                integralType = jinteger;
                break;
            case "double":
                integralType = jdouble;
                break;
            case "float":
                integralType = jfloat;
                break;
            case "long":
                integralType = jlong;
                break;
            case "string":
                integralType = jstring;
                break;
            case "object":
                integralType = jobject;
                break;
            case "boolean":
                integralType = jboolean;
                break;
            default:
                integralType = juserDefined;
                break;
        }
    }

    @Override
    public void EvaluateNode(ActivationFrame frame, Interpreter interpreter) {
    }

    public List<Node> getConditionalExpressions(){
        List<Node> inst = getInstructions();
        List<Node> conditionalExpressions = new ArrayList<>();

        int instCount = inst.size();
        for (int i = 0; i < instCount; i++) {
            Node node = inst.get( i );
            if ( node instanceof FinalNode) {
                FinalNode finalNode = (FinalNode) node;
                if (finalNode.dataString().equals("while") ||
                        finalNode.dataString().equals("if") ||
                        finalNode.dataString().equals("(")) {
                    continue;
                }
                else if (finalNode.dataString().equals( ")" )) {
                    break;
                }
            }
            else {
                conditionalExpressions.add( node );
            }
        }

        return conditionalExpressions;
    }
}
