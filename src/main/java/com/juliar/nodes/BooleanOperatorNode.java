package com.juliar.nodes;

import com.juliar.errors.Logger;
import com.juliar.interpreter.ActivationFrame;
import com.juliar.interpreter.Interpreter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by don on 4/1/17.
 */
@SuppressWarnings("serial")
public class BooleanOperatorNode extends NodeImpl {
    @Override
    public NodeType getType() {
        return NodeType.BooleanOperatorType;
    }

    public void evaluateBooleanOperatorNode(ActivationFrame frame, Interpreter interpreter){
        try {

            Node node = this;
            Node lvalue = null;
            boolean isEqualEqual;
            Node operatorTypeNode  = null;
            Node rvalue = null;
            // This is ugly code. Need to find a better way to
            // handle these cases.
            // Multiple ifs will only cause confusion.
            FinalNode updatedLvalue = null;

            int instructionCount = getInstructions().size();

            if ( instructionCount == 3) {

                lvalue = getInstructions().get( 0 );
                operatorTypeNode = getInstructions().get ( 1 );
                rvalue = getInstructions().get( 2 );

                if ( operatorTypeNode instanceof FinalNode && ((FinalNode) operatorTypeNode).dataString().equals( "==" ) ){
                    if ( lvalue instanceof VariableNode){
                       Node valueOfLValue = frame.getNodeFromFrameByVariableNode( (VariableNode)lvalue );
                       if ( valueOfLValue instanceof LiteralNode && rvalue instanceof LiteralNode) {
                           Node valueOfRvalue = rvalue.getInstructions().get( 0 );
                           isEqualEqual = ((LiteralNode) valueOfLValue).isEqual( ((LiteralNode)rvalue));
                           frame.pushReturnNode(null);
                       }


                    }

                }


                // frame.pushReturnNode( booleanNode );
            }
        }
        catch( Exception ex){
            Logger.log(ex.getMessage());
        }


    }
}
