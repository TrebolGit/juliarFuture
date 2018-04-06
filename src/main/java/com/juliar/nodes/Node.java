package com.juliar.nodes;

import com.juliar.interpreter.ActivationFrame;
import com.juliar.interpreter.Interpreter;

import java.util.List;
import java.util.Stack;

/**
 * Created by donreamey on 10/21/16.
 */
public interface Node {
    void addInst(Node parent, Node instruction);

    void addInst(Node instruction);

    void addInst(Stack<Node> contextStack, Node instruction);

    List<Node> getInstructions();

    NodeType getType();

    IntegralType getIntegralType();

    void setVariableTypeByIntegralType( IntegralType type);

    void setVariableType(String variableType);

    //void EvaluateNode(Node node);

    void EvaluateNode(ActivationFrame frame, Interpreter interpreter);

}
