/**
 * <p>Utilities for code generation.</p>
 * <p>We provide here a simple - one could say minimal - toolkit for generating code. Two classes,
 * {@link ClassGenerator} and {@link MethodGenerator} represent a class and a method to be generated.
 * Code generation usually starts by instantiating {@code ClassGenerator} and populating it with
 * specifications for fields, methods, etc. Once the instance is in a satisfactory state, a call
 * to its {@code ClassGenerator.asText(...)} method will generate java code as a {@link String}, that can be
 * saved to a file and checked with a {@link JavaCompiler} for syntax errors. Other classes comprise 
 * basic utilities and standard comments useful in code generation tasks.</p>
 * 
 * @author Jacques Gignoux - 18 mai 2021
 *
 */
package fr.cnrs.iees.omhtk.codeGeneration;