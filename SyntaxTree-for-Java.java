/**
 * These class declarations shall ILLUSTRATE the
 * definition of data structures to contain
 * a syntax tree of a sub-set(!) of Java.
 */

abstract class SyntaxTree extends TreeNode {
}

abstract class TreeNode {
    List<TreeNode> getChildren();
    Iterator<TreeNode> iterator(); // all subtrees
}

class JavaProgramm extends SyntaxTree {
    Optional<Package> package;
    List<ImportStatement> imports;
    List<ClassDeclaration> classes;
}

class ClassDeclaration extends TreeNode {
    String identifier;
    Optional<String> superClassOrInterface;
    List<FieldDeclaration> members;
    List<MethDeclaration> methods;
    List<ConstructorDeclarations> constructors;
}

class VarDecl extends TreeNode {
    String type; // native types and user-defined
    String identifier;

}

class FieldDeclaration extends VarDecl {
    Optional<String> modifier; // could be more than one
    Optional<Expression> initializer;
}

class MethDeclaration extends Decl {
    Optional<String> modifier; // could be more than one
    List<Decl> parameter;
    Block block;
}

class Block extends TreeNode {
    List<Statement> statements;
}

abstract class Statement {
};

class MethodCall extends Statement {
    String identifier;  // stores qualified name
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
