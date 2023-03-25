package frontend.finalproject;

public class NotificationUtils {
    // ================================================= Env file notifications =================================================
    public static final String ADDED_GLOBAL_VAR_NEW_TYPE_TITLE = "New global variable type";
    public static final String ADDED_GLOBAL_VAR_NEW_TYPE_TEXT = "You've successfully added a new global variable type!";
    public static final String ADDED_ENUM_VALUE_TITLE = "New enum value";
    public static final String ADDED_ENUM_VALUE_TEXT = "You've successfully added a new enum value!";
    public static final String ADDED_COMPOUND_VARIABLE_TITLE = "New compound variable";
    public static final String ADDED_COMPOUND_VARIABLE_TEXT = "You've successfully added a new compound variable!";
    public static final String ADDED_VARIABLE_DECLARATION_TITLE = "New variable declaration";
    public static final String ADDED_VARIABLE_DECLARATION_TEXT = "You've successfully added a new variable declaration!";
    public static final String ADDED_ASSIGNMENT_TITLE = "New assignment";
    public static final String ADDED_ASSIGMENT_TEXT = "You've successfully added a new assignment!";
    public static final String ADDED_STATE_TITLE = "New state";
    public static final String ADDED_STATE_TEXT = "You've successfully added a new state!";
    public static final String ADDED_CHANGE_TITLE = "New change";
    public static final String ADDED_CHANGE_TEXT = "You've successfully added a new change";
    public static final String CREATED_NEW_PROJECT_SUCCESS_TITLE = "Project created";
    public static final String CREATED_NEW_PROJECT_FAIL_TITLE = "Project creation failed";
    public static final String CREATED_NEW_PROJECT_SUCCESS_TEXT = "You've successfully created a new project!";
    public static final String CREATED_NEW_PROJECT_FAIL_TEXT = "Something has failed while trying to create a new project";
    public static final String DELETED_GLOBAL_VAR_TYPE_TEXT = "You've successfully deleted a global variable type!";
    public static final String DELETED_GLOBAL_VAR_TYPE_TITLE = "Global variable type deleted";
    public static final String DELETED_VAR_DECLARATION_TEXT = "You've successfully deleted a global variable declaration!";
    public static final String DELETED_VAR_DECLARATION_TITLE = "Global variable declaration deleted";
    public static final String DELETED_EX_CHANGE_DYN_MODEL_TITLE = "Ex change deleted";
    public static final String DELETED_EX_CHANGE_DYN_MODEL_TEXT = "You've successfully deleted an ex change dym model!";
    public static final String DELETED_INIT_BELIEF_STMT_TEXT = "You've successfully deleted an initial belief statement!";
    public static final String DELETED_INIT_BELIEF_STMT_TITLE = "Initial belief statement deleted";
    public static final String DELETED_SPECIAL_STATE_TEXT = "You've successfully deleted a special state";
    public static final String DELETED_SPECIAL_STATE_TITLE = "Special state deleted";

    // ================================================= Skill files notifications =================================================
    public static final String DELETED_GLOBAL_VAR_MODULE_TEXT = "You've successfully deleted global variable module parameter!";
    public static final String DELETED_GLOBAL_VAR_MODULE_TITLE = "Global variable parameter deleted";
    public static final String DELETED_GLOBAL_VAR_PRECONDITION_ASS_TEXT = "You've successfully deleted a global variable precondition assignment!";
    public static final String DELETED_GLOBAL_VAR_PRECONDITION_ASS_TITLE = "Global variable precondition assignment deleted";
    public static final String DELETED_PLANNER_ASS_PRECONDITION_TEXT = "You've successfully deleted a planner assignment precondition!";
    public static final String DELETED_PLANNER_ASS_PRECONDITION_TITLE = "Planner assignment precondition deleted";
    public static final String DELETED_DYNAMIC_MODEL_TEXT = "You've successfully deleted dynamic model!";
    public static final String DELETED_DYNAMIC_MODEL_TITLE = "Dynamic model deleted";
    public static final String DELETED_RESPONSE_RULE_TEXT = "You've successfully deleted response rule!";
    public static final String DELETED_RESPONSE_RULE_TITLE = "Response rule deleted";
    public static final String DELETED_IMPORT_CODE_TITLE = "Import code deleted";
    public static final String DELETED_IMPORT_CODE_TEXT = "You've successfully deleted import code!";
    public static final String DELETED_SERVICE_PARAM_TEXT = "You've successfully deleted service parameter!";
    public static final String DELETED_SERVICE_PARAM_TITLE = "Service parameter deleted";
    public static final String DELETED_LOCAL_VAR_INIT_TEXT = "You've successfully deleted local variable initialization!";
    public static final String DELETED_LOCAL_VAR_INIT_TITLE = "Local variable initialization deleted";
    public static final String ADD_SKILL_TITLE = "New skill";
    public static final String ADD_SKILL_SUCCESS_TEXT = "You've successfully added a new skill!";
    public static final String ADDED_IMPORT_CODE_TITLE = "Import code added";
    public static final String ADDED_IMPORT_CODE_TEXT = "You've successfully added import code!";
    public static final String ADDED_LOCAL_VAR_INIT_ROBOT_FRAMEWORK_TITLE = "Robot framework local init added";
    public static final String ADDED_LOCAL_VAR_INIT_ROBOT_FRAMEWORK_TEXT = "You've successfully added local var initialization from robot framework!";
    public static final String ADDED_IMPORT_VALUE_TITLE = "Import value added";
    public static final String ADDED_IMPORT_VALUE_TEXT = "You've successfully added import value!";
    public static final String ADDED_LOCAL_VAR_INIT_SKILL_CODE_RET_VALUE_TITLE = "Local var init skill code added";
    public static final String ADDED_LOCAL_VAR_INIT_SKILL_CODE_RET_VALUE_TEXT = "You've successfully added local var init from skill code return value!";
    public static final String ADDED_LOCAL_VAR_INIT_FROM_SD_TITLE = "Local var init from sd added";
    public static final String ADDED_LOCAL_VAR_INIT_FROM_SD_TEXT = "You've just added local var initialization from sd!";
    public static final String ADDED_SERVICE_PARAM_TITLE = "Service parameter added";
    public static final String ADDED_SERVICE_PARAM_TEXT = "You've successfully added service parameter!";
    public static final String ADDED_RESPONSE_RULE_TITLE = "Response rule added";
    public static final String ADDED_RESPONSE_RULE_TEXT = "You've successfully added a new response rule!";

    // ================================================= Other notifications =================================================
    public static final String GENERAL_ERROR_TITLE = "Error";

    public static final String SAVED_CHANGES_TO_PROJECT_TITLE = "Saving changes to skill";
    public static final String SAVED_CHANGES_TO_PROJECT_TEXT = "You've successfully saved your skill changes!";
    public static final String SAVED_CHANGES_TO_ENV_TITLE = "Saving changes to env file";
    public static final String SAVED_CHANGES_TO_ENV_TEXT = "You've successfully saved change to env file!";
    public static final String DELETED_GLOBAL_VAR_TYPE_FAIL_TITLE = "Failed to delete global variable type";
    public static final String DELETED_GLOBAL_VAR_TYPE_CHOOSE_GLOBAL_VAR_TEXT = "Please select a global variable type";
    public static final String ADDED_GLOBAL_VAR_TYPE_FAILED_TEXT = "Please add content about the global variable type you want to add";
    public static final String ADDED_GLOBAL_VAR_NEW_TYPE_FAILED_TITLE = "Missing information regarding the type";
    public static final String DELETED_VAR_DEC_FAIL_TEXT = "Please select a variable declaration";
    public static final String DELETE_VAR_DEC_TITLE_FAIL = "Failed to delete a variable declaration";
    public static final String DELETED_INIT_BELIEF_STMT_FAIL_TITLE = "Failed to delete initial belief state assignment";
    public static final String DELETED_INIT_BELIEF_STMT_FAIL_TEXT = "Please select an initial belief state assignment to delete";
    public static final String DELETED_EX_CHANGE_DYN_MODEL_FAIL_TEXT = "Please select an Ex Change dynamic model to delete";
    public static final String DELETED_EX_CHANGE_DYN_MODEL_FAIL_TITLE = "Failed to delete ex change dynamic model";
    public static final String DELETED_SPECIAL_STATE_FAIL_TEXT = "Please select a special state to delete";
    public static final String DELETED_SPECIAL_STATE_FAIL_TITLE = "Failed to delete special state";
    public static final String ADDED_ENUM_VALUE_FAILED_TITLE = "Failed to add enum value";
    public static final String ADDED_ENUM_VALUE_FAILED_TEXT = "Please add content about the enum value you want to add";
    public static final String REMOVED_ENUM_VALUE_TITLE = "Enum value removed";
    public static final String REMOVED_ENUM_VALUE_TEXT = "You've successfully removed an enum value!";
    public static final String REMOVED_ENUM_VALUE_FAILED_TITLE = "Failed to remove enum value";
    public static final String REMOVED_ENUM_VALUE_FAILED_TEXT = "Please select an enum value to remove";
    public static final String EDIT_GLOBAL_VAR_TYPE_FAIL_TITLE = "Failed to edit global variable type";
    public static final String EDIT_GLOBAL_VAR_TYPE_FAIL_TEXT = "Please select a global variable type to edit";

    public static final String ADD_VAR_TYPE_COMPOUND_TITLE = "Add Compound Variable Type";
    public static final String ADD_VAR_TYPE_COMPOUND_TEXT = "Compound variable type added successfully";
    public static final String ADD_VAR_TYPE_COMPOUND_FAILED_TITLE = "Add Compound Variable Type Failed";
    public static final String ADD_VAR_TYPE_COMPOUND_FAILED_TEXT = "Please fill all the fields";
}
