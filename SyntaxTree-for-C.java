/**
 * These class declarations shall ILLUSTRATE the
 * definition of data structures to contain
 * a syntax tree of a sub-set(!) of C.
 */

abstract class SyntaxTree extends TreeNode {
}

abstract class TreeNode {
    List<TreeNode> getChildren();
    Iterator<TreeNode> iterator(); // all subtrees
}

class CppProgram {
    List<Functions> functions;
    List<StructDeclarations> structs;
}

class StructDeclaration {
    String identifier;
    List<VarDeclaration> fields;
}

class VarDeclaration {
    Type type;
    String identifier;
    Optional<Expression> initializer;
}

class Declaration extends TreeNode {
    Type type;
    String identifier;
}

class AttributeDeclaration extends Declaration {
    Optional<String> modifiers;
}

class MethodDeclaration extends Declaration {
    Optional<String> modifiers;
    List<Declaration> parameters;
    Block body;
}

class Block extends TreeNode {
    List<Statement> statements;
}

abstract class Statement {
};

class MethodCall extends Statement {
    String identifier;
    List<Argument> arguments;
}

abstract class Argument extends TreeNode {
}

class Literal extends Argument {
    Object value; // String, Integer, Double, Boolean
}

class Variable extends Argument {
    String identifier;
}

class FieldAccessor extends Variable {
    Argument base;
}

class IndexedAccessor extends Argument {
    Argument base;
    Expression index;
}

class Return extends Statement {
    Optional<Expression> returnValue;
}

abstract class Loop extends Statement {
}

class For3Loop extends Loop {
    List<VarDeclaration> initializers;
    BooleanExpression condition;
    Statement incrementer;
    Block body;
}

class Conditional extends Loop {
    BooleanExpression condition;
    Block trueBody;
    Optional<Block> elseBody;
}

class Expression extends TreeNode {
    // to be defined
}
