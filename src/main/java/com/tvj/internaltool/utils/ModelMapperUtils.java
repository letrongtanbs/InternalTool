package com.tvj.internaltool.utils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

public class ModelMapperUtils {

    private static ModelMapper modelMapper = new ModelMapper();

    public static <S, D> D map(final S source, final Class<D> outClass) {
        return modelMapper.map(source, outClass);
    }

    public static <S, D> List<D> mapAll(final Collection<S> entityList, final Class<D> outCLass) {
        return entityList.stream().map(entity -> map(entity, outCLass)).collect(Collectors.toList());
    }

}
