package com.anonymous.finoanaapi.controllers.mapper;

import com.anonymous.finoanaapi.utils.exceptions.NotSupportedMapping;

public abstract class AbstractDomaineToRestMapper<D, R> {
  public D toDomain(R rest) throws NotSupportedMapping {
    throw new NotSupportedMapping();
  }

  public R toRest(D domain) throws NotSupportedMapping {
    throw new NotSupportedMapping();
  }
}
