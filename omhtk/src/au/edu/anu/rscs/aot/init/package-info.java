/**
 * Utilities to perform late initialisation (i.e., after instantiation) of objects.
 * Complex application often require that initialisation of instances is done in more than one step,
 * i.e. some operations must be done after instance construction before the instance can really be
 * used for what it has been designed for. The classes in this package rely on the 
 * {@link fr.ens.biologie.generic.Initialisable Initialisable} interface.
 * 
 * @author Jacques Gignoux - 18 mai 2021
 *
 */
package au.edu.anu.rscs.aot.init;
