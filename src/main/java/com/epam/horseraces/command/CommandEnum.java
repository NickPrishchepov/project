package com.epam.horseraces.command;

import com.epam.horseraces.command.impl.PerformActionCommand;
import com.epam.horseraces.command.impl.horse.ActionHorseCommand;
import com.epam.horseraces.command.impl.horse.AddHorseCommand;
import com.epam.horseraces.command.impl.horse.EditHorseCommand;
import com.epam.horseraces.command.impl.horse.ShowHorsesCommand;
import com.epam.horseraces.command.impl.prediction.*;
import com.epam.horseraces.command.impl.quotation.*;
import com.epam.horseraces.command.impl.race.ActionRaceCommand;
import com.epam.horseraces.command.impl.race.AddRaceCommand;
import com.epam.horseraces.command.impl.race.EditRaceCommand;
import com.epam.horseraces.command.impl.race.ShowCommand;
import com.epam.horseraces.command.impl.results.MakeResultsCommand;
import com.epam.horseraces.command.impl.user.*;

/**
 * The {@code CommandEnum} contains the list of main
 * commands that helps to organize the program execution
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public enum CommandEnum {

    SHOW_USERS {
        {
            this.command = new com.epam.horseraces.command.impl.user.ShowCommand();
        }
    },

    SHOW_HORSES {
        {
            this.command = new ShowHorsesCommand();
        }
    },

    ADD_HORSE {
        {
            this.command = new AddHorseCommand();
        }
    },

    EDIT_HORSE {
        {
            this.command = new EditHorseCommand();
        }
    },

    USER_ACTION {
        {
            this.command = new ActionUserCommand();
        }
    },

    HORSE_ACTION {
        {
            this.command = new ActionHorseCommand();
        }
    },

    SHOW_RACES {
        {
            this.command = new ShowCommand();
        }
    },

    SHOW_RACES_HISTORY {
        {
            this.command = new ShowCommand();
        }
    },

    SHOW_RACES_BEFORE_ADD_BET {
        {
            this.command = new ShowCommand();
        }
    },

    SET_BET_PARAMETERS {
        {
            this.command = new SetQuotationParametersCommand();
        }
    },

    SHOW_RACES_BEFORE_EDIT_BET {
        {
            this.command = new ShowCommand();
        }
    },

    SHOW_RACES_USER_SIDE {
        {
            this.command = new ShowCommand();
        }
    },

    SHOW_HORSES_USER_SIDE {
        {
            this.command = new ShowHorsesCommand();
        }
    },

    SHOW_RACES_BOOKMAKER_SIDE {
        {
            this.command = new ShowCommand();
        }
    },

    SHOW_HORSES_BOOKMAKER_SIDE {
        {
            this.command = new ShowHorsesCommand();
        }
    },

    SHOW_BETS_HISTORY {
        {
            this.command = new ShowQuotationsCommand();
        }
    },

    SHOW_PREDICTIONS_HISTORY {
        {
            this.command = new ShowPredictionsCommand();
        }
    },

    SHOW_PREDICTIONS {
        {
            this.command = new ShowPredictionsCommand();
        }
    },


    SET_RACES_PARAMETERS_BEFORE_ADD_BET {
        {
            this.command = new SetQuotationParametersCommand();
        }
    },

    SET_RACES_PARAMETERS_BEFORE_EDIT_BET {
        {
            this.command = new SetQuotationParametersCommand();
        }
    },

    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },

    ADD_RACE {
        {
            this.command = new AddRaceCommand();
        }
    },

    RACE_ACTION {
        {
            this.command = new ActionRaceCommand();
        }
    },

    EDIT_RACE {
        {
            this.command = new EditRaceCommand();
        }
    },

    EDIT_USER {
        {
            this.command = new EditCommand();
        }
    },

    SET_HORSE_PARAMETERS_BEFORE_ADD_BET {
        {
            this.command = new SetQuotationParametersCommand();
        }
    },

    QUOTATION_ACTION {
        {
            this.command = new ActionQuotationCommand();
        }
    },

    EDIT_BET {
        {
            this.command = new EditBetCommand();
        }
    },

    SHOW_QUOTATIONS_BEFORE_EDIT_PREDICTION {
        {
            this.command = new SetPredictionParametersCommand();
        }
    },

    SET_QUOTATION_PARAMETERS_BEFORE_ADD_PREDICTION {
        {
            this.command = new SetPredictionParametersCommand();
        }
    },

    ADD_PREDICTION {
        {
            this.command = new AddPredictionCommand();
        }
    },

    SET_QUOTATION_PARAMETERS_BEFORE_EDIT_PREDICTION {
        {
            this.command = new SetPredictionParametersCommand();
        }
    },

    EDIT_PREDICTION {
        {
            this.command = new EditPredictionCommand();
        }
    },

    PREDICTION_ACTION {
        {
            this.command = new ActionPredictionCommand();
        }
    },

    ADD_BET {
        {
            this.command = new AddBetCommand();
        }
    },

    SET_HORSE_PARAMETERS_BEFORE_EDIT_BET {
        {
            this.command = new SetQuotationParametersCommand();
        }
    },

    SHOW_QUOTATIONS_BEFORE_ADD_PREDICTION {
        {
            this.command = new ShowQuotationsCommand();
        }
    },

    EDITPREDICTION {
        {
            this.command = new ShowPredictionsCommand();
        }
    },

    DELETEPREDICTION {
        {
            this.command = new ShowQuotationsCommand();
        }
    },

    DELETEPRED {
        {
            this.command = new DeletePredictionCommand();
        }
    },

    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },


    REGISTRATION {
        {
            this.command = new Registration();
        }
    },


    DELETE {
        {
            this.command = new com.epam.horseraces.command.impl.user.ShowCommand();
        }
    },

    EDIT {
        {
            this.command = new com.epam.horseraces.command.impl.user.ShowCommand();
        }
    },

    MAKERESULT {
        {
            this.command = new MakeResultsCommand();
        }
    },

    LOGREG {
        {
            this.command = new PerformActionCommand();
        }
    },


    EDITRACE {
        {
            this.command = new ShowCommand();
        }
    },

    DELETEHORSE {
        {
            this.command = new ShowHorsesCommand();
        }
    },

    EDITHORSE {
        {
            this.command = new ShowHorsesCommand();
        }
    };


    /**
     * Describes the command from the
     * {@code CommandEnum} list
     */
    ActionCommand command;

    /**
     * @return One of the existing command from the
     * {@code CommandEnum} list
     */
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
