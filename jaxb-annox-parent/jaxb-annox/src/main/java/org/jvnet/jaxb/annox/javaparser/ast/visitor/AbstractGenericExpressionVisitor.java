package org.jvnet.jaxb.annox.javaparser.ast.visitor;

import com.github.javaparser.ast.ArrayCreationLevel;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.CompactConstructorDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.ReceiverParameter;
import com.github.javaparser.ast.body.RecordDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.InstanceOfExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.LiteralExpr;
import com.github.javaparser.ast.expr.LiteralStringValueExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.PatternExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.SuperExpr;
import com.github.javaparser.ast.expr.SwitchExpr;
import com.github.javaparser.ast.expr.TextBlockLiteralExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.expr.TypeExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.modules.ModuleDeclaration;
import com.github.javaparser.ast.modules.ModuleExportsDirective;
import com.github.javaparser.ast.modules.ModuleOpensDirective;
import com.github.javaparser.ast.modules.ModuleProvidesDirective;
import com.github.javaparser.ast.modules.ModuleRequiresDirective;
import com.github.javaparser.ast.modules.ModuleUsesDirective;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.LabeledStmt;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.stmt.LocalRecordDeclarationStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.SynchronizedStmt;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.stmt.UnparsableStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.stmt.YieldStmt;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.IntersectionType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.type.UnionType;
import com.github.javaparser.ast.type.UnknownType;
import com.github.javaparser.ast.type.VarType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.type.WildcardType;
import com.github.javaparser.ast.visitor.GenericVisitor;

public abstract class AbstractGenericExpressionVisitor<R, A> implements
		GenericVisitor<R, A> {

	public abstract R visitDefault(Node n, A arg);

	public R visitDefault(Expression n, A arg)
	{
		return visitDefault((Node) n, arg);
	}

	public R visitDefault(LiteralExpr n, A arg)
	{
		return visitDefault((Expression) n, arg);
	}

	public R visitDefault(LiteralStringValueExpr n, A arg)
	{
		return visitDefault((LiteralExpr) n, arg);
	}

	public R visitDefault(IntegerLiteralExpr n, A arg)
	{
		return visitDefault((LiteralStringValueExpr) n, arg);
	}

	public R visitDefault(LongLiteralExpr n, A arg)
	{
		return visitDefault((LiteralStringValueExpr) n, arg);
	}

	public R visitDefault(NameExpr n, A arg) {
		return visitDefault((Expression) n, arg);
	}

	@Override
	public R visit(CompilationUnit n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(PackageDeclaration n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(TypeParameter n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(LineComment n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(BlockComment n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ClassOrInterfaceDeclaration n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(EnumDeclaration n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(RecordDeclaration n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(CompactConstructorDeclaration n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(EnumConstantDeclaration n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(AnnotationDeclaration n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(AnnotationMemberDeclaration n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(FieldDeclaration n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(VariableDeclarator n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ConstructorDeclaration n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(MethodDeclaration n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(Parameter n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(InitializerDeclaration n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(JavadocComment n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ClassOrInterfaceType n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(PrimitiveType n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ArrayType n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ArrayCreationLevel n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(IntersectionType n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(UnionType n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(VoidType n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(WildcardType n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(UnknownType n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ArrayAccessExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ArrayCreationExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ArrayInitializerExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(AssignExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(BinaryExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(CastExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ClassExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ConditionalExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(EnclosedExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(FieldAccessExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(InstanceOfExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(StringLiteralExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(IntegerLiteralExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(LongLiteralExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(CharLiteralExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(DoubleLiteralExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(BooleanLiteralExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(NullLiteralExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(MethodCallExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(NameExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ObjectCreationExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ThisExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(SuperExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(UnaryExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(VariableDeclarationExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(MarkerAnnotationExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(SingleMemberAnnotationExpr n, A arg) {
		return visitDefault(n, arg);
	}


	public R visitDefault(AnnotationExpr n, A arg)
	{
		return visitDefault((Expression) n, arg);
	}

	@Override
	public R visit(NormalAnnotationExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(MemberValuePair n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ExplicitConstructorInvocationStmt n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(LocalClassDeclarationStmt n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(LocalRecordDeclarationStmt n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(AssertStmt n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(BlockStmt n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(LabeledStmt n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(EmptyStmt n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ExpressionStmt n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(SwitchStmt n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(SwitchEntry n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(BreakStmt n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ReturnStmt n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(IfStmt n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(WhileStmt n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ContinueStmt n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(DoStmt n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ForEachStmt n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ForStmt n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ThrowStmt n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(SynchronizedStmt n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(TryStmt n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(CatchClause n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(LambdaExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(MethodReferenceExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(TypeExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(NodeList n, A arg) {
		for (final Object v : n) {
			R result = ((Node) v).accept(this, arg);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	@Override
	public R visit(Name n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(SimpleName n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ImportDeclaration n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ModuleDeclaration n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ModuleRequiresDirective n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ModuleExportsDirective n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ModuleProvidesDirective n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ModuleUsesDirective n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ModuleOpensDirective n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(UnparsableStmt n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(ReceiverParameter n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(VarType n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(Modifier n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(SwitchExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(YieldStmt n, A arg) {
		return visitDefault(n, arg);
	}
	@Override
	public R visit(TextBlockLiteralExpr n, A arg) {
		return visitDefault(n, arg);
	}

	@Override
	public R visit(PatternExpr n, A arg) {
		return visitDefault(n, arg);
	}
}
