package org.example.config;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;
import org.example.dao.BuildingDAO;
import org.example.dao.CompanyDAO;
import org.example.dao.EmployDAO;
import org.example.resources.BuildingResource;
import org.example.resources.CompanyResource;
import org.example.resources.EmployResource;
import org.example.services.BuildingServices;
import org.example.services.CompanyServices;
import org.example.services.EmployServices;

public class HManagerApplication extends Application<HManagerConfiguration> {

  public static void main(final String[] args) throws Exception {
    new HManagerApplication().run(args);
  }

  @Override
  public String getName() {
    return "HManager";
  }

  @Override
  public void run(final HManagerConfiguration configuration, final Environment environment) {

    // Company endpoint
    final CompanyDAO companyDAO = new CompanyDAO(configuration.getConnection());
    final CompanyServices companyServices = new CompanyServices(companyDAO);
    environment.jersey().register(new CompanyResource(companyServices));

    // Building endpoint
    final BuildingDAO buildingDAO = new BuildingDAO(configuration.getConnection());
    final BuildingServices buildingServices = new BuildingServices(buildingDAO);
    environment.jersey().register(new BuildingResource(buildingServices));

    // Employ endpoint
    final EmployDAO employDAO = new EmployDAO(configuration.getConnection());
    final EmployServices employServices = new EmployServices(employDAO);
    environment.jersey().register(new EmployResource(employServices));

    // FlatInfo endpoint
    //        final FlatInfoDAO flatInfoDAO = new FlatInfoDAO(configuration.getConnection());
    //        final FlatInfoServices flatInfoServices = new FlatInfoServices(flatInfoDAO);
    //        environment.jersey().register(new FlatInfoResource(flatInfoServices));
  }
}
