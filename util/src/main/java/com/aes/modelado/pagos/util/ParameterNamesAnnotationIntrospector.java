package com.aes.modelado.pagos.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.databind.introspect.*;

import java.lang.reflect.MalformedParametersException;
import java.lang.reflect.Parameter;

class ParameterNamesAnnotationIntrospector extends NopAnnotationIntrospector {
    private static final long serialVersionUID = 1L;
    private final Mode creatorBinding;
    private final ParameterExtractor parameterExtractor;

    ParameterNamesAnnotationIntrospector(Mode creatorBinding, ParameterExtractor parameterExtractor) {
        this.creatorBinding = creatorBinding;
        this.parameterExtractor = parameterExtractor;
    }

    public String findImplicitPropertyName(AnnotatedMember m) {
        return m instanceof AnnotatedParameter?this.findParameterName((AnnotatedParameter)m):null;
    }

    public Mode findCreatorBinding(Annotated a) {
        JsonCreator ann = (JsonCreator)a.getAnnotation(JsonCreator.class);
        return ann != null?ann.mode():this.creatorBinding;
    }

    private String findParameterName(AnnotatedParameter annotatedParameter) {
        Parameter[] params;
        try {
            params = this.getParameters(annotatedParameter.getOwner());
        } catch (MalformedParametersException var4) {
            return null;
        }

        Parameter p = params[annotatedParameter.getIndex()];
        return p.isNamePresent()?p.getName():null;
    }

    private Parameter[] getParameters(AnnotatedWithParams owner) {
        return owner instanceof AnnotatedConstructor?this.parameterExtractor.getParameters(((AnnotatedConstructor)owner).getAnnotated()):(owner instanceof AnnotatedMethod?this.parameterExtractor.getParameters(((AnnotatedMethod)owner).getAnnotated()):null);
    }
}