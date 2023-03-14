package com.gcs.cmp.providers.HetznerV0;

import java.sql.SQLException;

public interface Hetzner_ServiceV0 {

	public Object getAllImages() throws SQLException;
	public Object getAllLocations() throws SQLException;
	public Object getAllServers() throws SQLException;
	public Object getServer(Long id) throws SQLException;
	public Object getAllActionsForServer(Long id) throws SQLException;
	public Object getAllServerTypes() throws SQLException;
}
