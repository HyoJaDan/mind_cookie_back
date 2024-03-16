package hobbit.controller.PersonalChallenge;
import hobbit.domain.EtcGoal;
import hobbit.domain.Exercise;
import hobbit.domain.PersonalChallenge;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PersonalChallengeDTO {
        private Long id;
        private List<EtcGoalDto> etcGoals;
        private ExerciseDto exercise;

        public PersonalChallengeDTO(PersonalChallenge personalChallenge) {
            this.id = personalChallenge.getId();
            this.etcGoals = personalChallenge.getEtcGoals().stream()
                    .map(EtcGoalDto::new)
                    .collect(Collectors.toList());
            this.exercise = new ExerciseDto(personalChallenge.getExercise());
        }

        @Getter
        @Setter
        static class EtcGoalDto {
            private Long id;
            private String goalName;
            private boolean done;

            public EtcGoalDto(EtcGoal etcGoal) {
                this.id = etcGoal.getId();
                this.goalName = etcGoal.getGoalName();
                this.done = etcGoal.isDone();
            }
        }

        @Getter
        @Setter
        static class ExerciseDto {
            private double exerciseCalorie;
            private int durationInSeconds;
            private boolean done;

            public ExerciseDto(Exercise exercise) {
                this.exerciseCalorie = exercise.getExerciseCalorie();
                this.durationInSeconds=exercise.getDurationInSeconds();
                this.done = exercise.isDone();
            }
        }


}
