package io.woori.account.wooriaccount.domain.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Getter
@Table(name = "customers_roles")
public class CustomerRole {


    @EmbeddedId
    private CustomerRolePk customerRolePk;


    @MapsId("customerId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;


    @MapsId("roleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;


    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class CustomerRolePk implements Serializable {

        @Column(name="customer_id", nullable = false)
        private Long customerId;

        @Column(name = "role_id", nullable = false)
        private Long roleId;

    }
}
