package org.example.config;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;
import org.example.dao.*;
import org.example.resources.*;
import org.example.services.*;

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
    final FlatDAO flatInfoDAO = new FlatDAO(configuration.getConnection());
    final FlatInfoServices flatInfoServices = new FlatInfoServices(flatInfoDAO);
    environment.jersey().register(new FlatInfoResource(flatInfoServices));

    // Resident endpoint
    final ResidentsDAO residentsDAO = new ResidentsDAO(configuration.getConnection());
    final ResidentsServices residentsServices = new ResidentsServices(residentsDAO);
    environment.jersey().register(new ResidentsResource(residentsServices));
  }
}
