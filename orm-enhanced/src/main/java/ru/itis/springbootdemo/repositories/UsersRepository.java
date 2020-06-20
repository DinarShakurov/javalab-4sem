package ru.itis.springbootdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.springbootdemo.dto.InformationDto;
import ru.itis.springbootdemo.models.User;

public interface UsersRepository extends JpaRepository<User, Long> {
    // JPQL
    @Query("select new ru.itis.springbootdemo.dto.InformationDto(user.name, (sum(document.size) / 1024 / 1024) ) from User user left join user.documents " +
            "as document where user.id = :userId group by user.id")
    InformationDto getInformationByUser(@Param("userId") Long userId);
}
