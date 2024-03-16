package hobbit.controller.PersonalChallenge;

import hobbit.controller.PersonalChallenge.PersonalChallengeDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalChallengeStatusDTO {
    private String status; // "before", "active", "after"
    private PersonalChallengeDTO challenge;

    // Constructor for status without challenge data
    public PersonalChallengeStatusDTO(String status) {
        this.status = status;
    }

    // Constructor for status with challenge data
    public PersonalChallengeStatusDTO(String status, PersonalChallengeDTO challenge) {
        this.status = status;
        this.challenge = challenge;
    }
}
