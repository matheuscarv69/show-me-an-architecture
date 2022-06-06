package src.infrastructure.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class AbstractModel<T> {

    public abstract T toDomain();

}
