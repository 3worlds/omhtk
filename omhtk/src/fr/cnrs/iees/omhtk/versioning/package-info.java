/**
 * Tools for the management of version numbers and library dependencies in a java project.
 * <p>The version management system proposed here relies on <a href="https://ant.apache.org/ivy/">ivy</a>. 
 * It involves:</p>
 * <ul>
 * <li>A class to increase the version number: {@code VersionManager}</li>
 * <li>A class to store the project-specific ivy dependencies: {@code VersionSettings}</li>
 * <li>A valid ivy setup, i.e. <em>ivy.xml</em> and <em>build.xml</em> files, that must be stored 
 * in a <em>&lt;project&gt;/scripts</em> directory</li>
 * </ul>
 * <p>The GenerateVersionManager class will copy the {@code VersionManager} and 
 * {@code VersionSettings} classes in a new project. You will then need to edit {@code VersionSettings}
 * and launch {@code VersionManager} to generate the proper ivy files for dependency management.</p>
 *  
 * @author Jacques Gignoux - 18 mai 2021
 *
 */
package fr.cnrs.iees.omhtk.versioning;