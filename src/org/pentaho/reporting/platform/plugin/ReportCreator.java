/*
 * This program is free software; you can redistribute it and/or modify it under the 
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software 
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this 
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html 
 * or from the Free Software Foundation, Inc., 
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright 2008 Pentaho Corporation.  All rights reserved.
 */
package org.pentaho.reporting.platform.plugin;

import java.io.IOException;
import java.util.HashMap;

import org.pentaho.platform.api.engine.IPentahoSession;
import org.pentaho.platform.api.repository.ISolutionRepository;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.libraries.resourceloader.FactoryParameterKey;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.libraries.resourceloader.ResourceKey;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;

public class ReportCreator
{
  public static MasterReport createReport(String reportDefinitionPath,
                                          IPentahoSession pentahoSession) throws ResourceException, IOException
  {
    ResourceManager resourceManager = new ResourceManager();
    resourceManager.registerDefaults();
    HashMap helperObjects = new HashMap();
    // add the runtime context so that PentahoResourceData class can get access
    // to the solution repo
    helperObjects.put(new FactoryParameterKey(RepositoryResourceData.PENTAHO_REPOSITORY_KEY), PentahoSystem.get(ISolutionRepository.class, pentahoSession));
    ResourceKey key = resourceManager.createKey(RepositoryResourceLoader.SOLUTION_SCHEMA_NAME + RepositoryResourceLoader.SCHEMA_SEPARATOR
        + reportDefinitionPath, helperObjects);
    Resource resource = resourceManager.create(key, null, MasterReport.class);
    return (MasterReport) resource.getResource();
  }

}