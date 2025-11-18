
Insert into M9USER
   (USERID, USERNAME, PASSWORD, EMAILID, USERTYPE, 
    ISINTERNAL, ISDELETED)
 Values
   (1, 'admin', '3f1a6cb90511f37b3060a2c3b63ab88b665444f4f2db3313417dca83a8db89c7', 'aa@aa.com', 0, 
    0, 0);
COMMIT;

Insert into M9USER
   (USERID, USERNAME, PASSWORD, EMAILID, USERTYPE, 
    ISINTERNAL, ISDELETED)
 Values
   (2, 'martadmin', '0762f703a8c86f98461bc79f70d95f78a78dff1950c107b779d20629934ab4d5', 'aa@aa.com', 0, 
    0, 0);
COMMIT;


Insert into M9VERSION
   (A_APPNAME, A_MAXVERSION, A_MINVERSION)
 Values
   ('Mart Server', 9, 70);
COMMIT;


Insert into M9VERSIONCOMPATIBLE
   (VC_VERSION)
 Values
   ('9.70.00');
COMMIT;


Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('modifyPermissions', 'Modify Permissions');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('viewPermissions', 'View Permissions');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('createMarkedVersion', 'Create Marked Version');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('updateLock', 'Update Lock');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('delete', 'Delete');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('changeSettings', 'Change Settings');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('modifyUser', 'Modify User');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('maintainmultipleversions', 'Permit Version Control');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('view', 'View');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('sharedLock', 'Shared Lock');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('viewUsers', 'View Users');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('create', 'Create');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('viewCatalogs', 'View Catalogs');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('unlock', 'Remove locks owned by others');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('endSession', 'End Session');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('existenceLock', 'Existence Lock');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('exclusiveLock', 'Exclusive Lock');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('deleteProfile', 'Delete Profile');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('viewSessions', 'View Sessions');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('modifyProfile', 'Modify Profile');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('modify', 'Modify');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('hide', 'Hide');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('rename', 'Rename');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('updateDescription', 'Update Description');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('deleteUser', 'Delete User');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('createProfile', 'Create Profile');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('createUser', 'Create User');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('viewProfiles', 'View Profiles');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('viewSettings', 'View Settings');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('unhide', 'Unhide');
Insert into M9ACTION
   (A_NAME, A_DISPLAYNAME)
 Values
   ('open', 'Open');
COMMIT;

Insert into M9CATALOG
   (C_ID, C_CONTAINER_ID, C_NAME, C_TYPE, C_DESCRIPTION, 
    C_ISHIDDEN, C_CREATEDON, C_LONGID, U_ID, C_VERSION, 
    C_PATH, C_ISVERSIONING, C_SCHEMASIGNATURE)
 Values
   (1, 0, 'Mart', 'M', 'Mart - root entry', 
    'N', TO_DATE('08/24/2016 18:53:50', 'MM/DD/YYYY HH24:MI:SS'), '1', 1, 0, 
    NULL, 'Y', NULL);
COMMIT;


Insert into M9CONTROL
   (CONTROLID, CONTROLNAME, CONTROLVALUE)
 Values
   (1, 'Default for session trace', 0);
Insert into M9CONTROL
   (CONTROLID, CONTROLNAME, CONTROLVALUE)
 Values
   (2, 'Object id generator', 65536);
Insert into M9CONTROL
   (CONTROLID, CONTROLNAME, CONTROLVALUE)
 Values
   (3, 'Session id generator', 0);
Insert into M9CONTROL
   (CONTROLID, CONTROLNAME, CONTROLVALUE)
 Values
   (4, 'Access id generator', 0);
Insert into M9CONTROL
   (CONTROLID, CONTROLNAME, CONTROLVALUE)
 Values
   (5, 'Trace id generator', 0);
COMMIT;


Insert into M9LOCKPOLICY
   (ACLOCKTYPE, EXLOCKTYPE)
 Values
   ('E', 'E');
Insert into M9LOCKPOLICY
   (ACLOCKTYPE, EXLOCKTYPE)
 Values
   ('E', 'S');
Insert into M9LOCKPOLICY
   (ACLOCKTYPE, EXLOCKTYPE)
 Values
   ('E', 'U');
Insert into M9LOCKPOLICY
   (ACLOCKTYPE, EXLOCKTYPE)
 Values
   ('S', 'E');
Insert into M9LOCKPOLICY
   (ACLOCKTYPE, EXLOCKTYPE)
 Values
   ('S', 'S');
Insert into M9LOCKPOLICY
   (ACLOCKTYPE, EXLOCKTYPE)
 Values
   ('U', 'E');
Insert into M9LOCKPOLICY
   (ACLOCKTYPE, EXLOCKTYPE)
 Values
   ('E', 'N');
Insert into M9LOCKPOLICY
   (ACLOCKTYPE, EXLOCKTYPE)
 Values
   ('S', 'N');
Insert into M9LOCKPOLICY
   (ACLOCKTYPE, EXLOCKTYPE)
 Values
   ('U', 'N');
Insert into M9LOCKPOLICY
   (ACLOCKTYPE, EXLOCKTYPE)
 Values
   ('X', 'N');
COMMIT;


Insert into M9OPTION
   (O_ID, O_KEY, O_VALUE)
 Values
   (1, 'USE_DEFAULT_PASSWORD', 'TRUE');
Insert into M9OPTION
   (O_ID, O_KEY, O_VALUE)
 Values
   (2, 'SMTP_HOST_NAME', NULL);
Insert into M9OPTION
   (O_ID, O_KEY, O_VALUE)
 Values
   (3, 'DEFAULT_PASSWORD', 'erwin');
Insert into M9OPTION
   (O_ID, O_KEY, O_VALUE)
 Values
   (4, 'ProfileOfCreator', '3');
Insert into M9OPTION
   (O_ID, O_KEY, O_VALUE)
 Values
   (5, 'SMTP_PORT_NUMBER', '-1');
Insert into M9OPTION
   (O_ID, O_KEY, O_VALUE)
 Values
   (6, 'NO_REPLY_EMAIL', NULL);
Insert into M9OPTION
   (O_ID, O_KEY, O_VALUE)
 Values
   (7, 'SMTP_PASSWORD', NULL);
Insert into M9OPTION
   (O_ID, O_KEY, O_VALUE)
 Values
   (8, 'SMTP_AUTHENTICATED', 'FALSE');
Insert into M9OPTION
   (O_ID, O_KEY, O_VALUE)
 Values
   (9, 'USE_SMTP_SETTINGS', 'FALSE');
Insert into M9OPTION
   (O_ID, O_KEY, O_VALUE)
 Values
   (10, 'ADMIN_EMAIL', NULL);
Insert into M9OPTION
   (O_ID, O_KEY, O_VALUE)
 Values
   (11, 'USER_SESSION_TIMEOUT', '1440');
Insert into M9OPTION
   (O_ID, O_KEY, O_VALUE)
 Values
   (12, 'SMTP_USER_NAME', NULL);
COMMIT;



Insert into M9PROFILE
   (P_ID, P_NAME, P_DESC, P_IS_BUILTIN)
 Values
   (1, 'Architect', 'This profile is for Architects', 1);
Insert into M9PROFILE
   (P_ID, P_NAME, P_DESC, P_IS_BUILTIN)
 Values
   (2, 'Viewer', 'This profile is for Model Viewers', 1);
Insert into M9PROFILE
   (P_ID, P_NAME, P_DESC, P_IS_BUILTIN)
 Values
   (3, 'Admin', 'This profile is for Administrators', 1);
Insert into M9PROFILE
   (P_ID, P_NAME, P_DESC, P_IS_BUILTIN)
 Values
   (4, 'Modeler', 'This profile is for Data Modelers', 1);
COMMIT;


Insert into M9PROFILEASSIGNMENT
   (C_ID, P_ID, USERID)
 Values
   (1, 3, 1);
COMMIT;


Insert into M9PROFILEASSIGNMENT
   (C_ID, P_ID, USERID)
 Values
   (1, 3, 2);
COMMIT;


Insert into M9SESSION
   (S_ID, U_ID, S_START, S_END, S_STATUS, 
    S_MACHINE)
 Values
   (1, 1, TO_DATE('08/24/2016 18:54:01', 'MM/DD/YYYY HH24:MI:SS'), NULL, 'A', 
    NULL);
COMMIT;


Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('model', NULL, 'Model');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('modelingObject', 'model', 'Modeling Object');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('udp', 'modelingObject', 'User Defined Property');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('userManagement', NULL, 'User Management');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('reports', NULL, 'Reports');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('Entity', 'modelingObject', 'Entity');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('ER_Diagram', 'modelingObject', 'ER Diagram');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('Entity.Attribute', 'Entity', 'Attribute');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('Data_Source_Object', 'modelingObject', 'Data Source Object');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('Data_Source_Object.Data_Source_Table', 'Data_Source_Object', 'Data Source Table');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('library', NULL, 'Library');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('Validation_Rule', 'modelingObject', 'Validation Rule');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('lockLibrary', 'library', 'Locking');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('permissionManagement', NULL, 'Permission Management');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('Data_Movement_Rule', 'modelingObject', 'Data Movement Rule');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('mart', NULL, 'Mart');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('sessionManagement', NULL, 'Session Management');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('lockMart', 'mart', 'Locking');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('version', 'model', 'Version');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('View', 'modelingObject', 'View');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('Default', 'modelingObject', 'Default Value');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('lockModel', 'model', 'Locking');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('Data_Source_Object.Data_Source_Table.Data_Source_Column', 'Data_Source_Object.Data_Source_Table', 'Data Source Column');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('profileManagement', 'permissionManagement', 'Profile Management');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('Relationship', 'modelingObject', 'Relationship');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('lockVersion', 'version', 'Locking');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('catalogManagement', NULL, 'Catalog Management');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('Theme', 'modelingObject', 'Theme');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('Subject_Area', 'modelingObject', 'Subject Area');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('Subject_Area.ER_Diagram', 'Subject_Area', 'ER Diagram');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('Entity.Key_Group', 'Entity', 'Key Group');
Insert into M9SECURABLE
   (S_NAME, S_PARENTNAME, S_DISPLAYNAME)
 Values
   ('Domain', 'modelingObject', 'Domain');
COMMIT;


Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (1, 'delete', 'Subject_Area');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (2, 'modify', 'Entity.Attribute');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (3, 'modifyUser', 'userManagement');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (4, 'updateLock', 'lockLibrary');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (5, 'modify', 'Relationship');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (6, 'modify', 'Data_Movement_Rule');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (7, 'sharedLock', 'lockLibrary');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (8, 'deleteProfile', 'profileManagement');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (9, 'delete', 'View');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (10, 'updateLock', 'lockModel');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (11, 'sharedLock', 'lockModel');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (12, 'create', 'Data_Source_Object');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (13, 'modify', 'View');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (14, 'createProfile', 'profileManagement');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (15, 'viewSessions', 'sessionManagement');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (16, 'modify', 'Default');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (17, 'changeSettings', 'mart');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (18, 'view', 'reports');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (19, 'delete', 'Data_Source_Object');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (20, 'create', 'Data_Source_Object.Data_Source_Table');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (21, 'create', 'View');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (22, 'createMarkedVersion', 'version');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (23, 'delete', 'Data_Movement_Rule');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (24, 'open', 'catalogManagement');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (25, 'create', 'Subject_Area');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (26, 'create', 'udp');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (27, 'viewUsers', 'userManagement');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (28, 'delete', 'Relationship');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (29, 'delete', 'Entity.Attribute');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (30, 'modify', 'Data_Source_Object');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (31, 'unlock', 'lockModel');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (32, 'delete', 'mart');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (33, 'viewSettings', 'mart');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (34, 'exclusiveLock', 'lockMart');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (35, 'unlock', 'lockMart');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (36, 'delete', 'Entity');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (37, 'rename', 'library');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (38, 'existenceLock', 'lockVersion');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (39, 'updateLock', 'lockMart');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (40, 'create', 'Theme');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (41, 'updateDescription', 'model');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (42, 'unlock', 'lockVersion');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (43, 'createUser', 'userManagement');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (44, 'create', 'Validation_Rule');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (45, 'create', 'Relationship');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (46, 'delete', 'ER_Diagram');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (47, 'modify', 'Subject_Area');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (48, 'create', 'Entity.Attribute');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (49, 'delete', 'udp');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (50, 'endSession', 'sessionManagement');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (51, 'modifyProfile', 'profileManagement');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (52, 'create', 'model');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (53, 'create', 'Default');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (54, 'existenceLock', 'lockLibrary');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (55, 'modify', 'Theme');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (56, 'sharedLock', 'lockVersion');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (57, 'delete', 'Data_Source_Object.Data_Source_Table');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (58, 'modify', 'model');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (59, 'viewPermissions', 'permissionManagement');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (60, 'exclusiveLock', 'lockVersion');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (61, 'delete', 'version');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (62, 'modify', 'ER_Diagram');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (63, 'create', 'Domain');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (64, 'hide', 'version');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (65, 'modify', 'Validation_Rule');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (66, 'delete', 'Validation_Rule');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (67, 'existenceLock', 'lockModel');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (68, 'existenceLock', 'lockMart');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (69, 'updateLock', 'lockVersion');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (70, 'delete', 'Data_Source_Object.Data_Source_Table.Data_Source_Column');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (71, 'modify', 'Entity.Key_Group');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (72, 'unhide', 'version');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (73, 'unlock', 'lockLibrary');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (74, 'viewProfiles', 'profileManagement');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (75, 'create', 'library');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (76, 'modifyPermissions', 'permissionManagement');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (77, 'exclusiveLock', 'lockModel');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (78, 'delete', 'Domain');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (79, 'delete', 'model');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (80, 'modify', 'Domain');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (81, 'deleteUser', 'userManagement');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (82, 'delete', 'Subject_Area.ER_Diagram');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (83, 'rename', 'model');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (84, 'maintainmultipleversions', 'model');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (85, 'updateDescription', 'version');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (86, 'modify', 'Data_Source_Object.Data_Source_Table.Data_Source_Column');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (87, 'delete', 'Theme');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (88, 'delete', 'Default');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (89, 'create', 'Entity.Key_Group');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (90, 'modify', 'Subject_Area.ER_Diagram');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (91, 'view', 'model');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (92, 'delete', 'library');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (93, 'exclusiveLock', 'lockLibrary');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (94, 'modify', 'Entity');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (95, 'create', 'ER_Diagram');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (96, 'sharedLock', 'lockMart');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (97, 'delete', 'Entity.Key_Group');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (98, 'rename', 'mart');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (99, 'modify', 'udp');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (100, 'create', 'Subject_Area.ER_Diagram');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (101, 'modify', 'Data_Source_Object.Data_Source_Table');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (102, 'rename', 'version');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (103, 'updateDescription', 'mart');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (104, 'create', 'Data_Source_Object.Data_Source_Table.Data_Source_Column');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (105, 'updateDescription', 'library');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (106, 'create', 'Data_Movement_Rule');
Insert into M9PERMISSION
   (PERMISSION_ID, A_NAME, S_NAME)
 Values
   (107, 'create', 'Entity');
COMMIT;


Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (1, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (2, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (4, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (5, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (6, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (7, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (9, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (10, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (11, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (12, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (13, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (16, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (18, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (19, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (20, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (21, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (22, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (23, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (24, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (25, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (26, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (28, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (29, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (30, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (31, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (36, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (37, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (38, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (40, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (41, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (42, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (44, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (45, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (46, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (47, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (48, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (49, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (52, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (53, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (54, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (55, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (56, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (57, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (58, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (60, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (61, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (62, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (63, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (64, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (65, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (66, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (67, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (69, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (70, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (71, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (72, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (73, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (75, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (77, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (78, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (79, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (80, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (82, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (83, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (84, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (85, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (86, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (87, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (88, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (89, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (90, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (91, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (92, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (93, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (94, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (95, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (97, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (99, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (100, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (101, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (102, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (104, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (105, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (106, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (107, 1);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (18, 2);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (67, 2);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (91, 2);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (1, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (2, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (3, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (4, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (5, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (6, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (7, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (8, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (9, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (10, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (11, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (12, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (13, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (14, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (15, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (16, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (17, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (18, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (19, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (20, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (21, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (22, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (23, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (24, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (25, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (26, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (27, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (28, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (29, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (30, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (31, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (32, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (33, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (34, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (35, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (36, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (37, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (38, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (39, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (40, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (41, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (42, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (43, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (44, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (45, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (46, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (47, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (48, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (49, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (50, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (51, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (52, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (53, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (54, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (55, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (56, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (57, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (58, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (59, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (60, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (61, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (62, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (63, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (64, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (65, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (66, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (67, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (68, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (69, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (70, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (71, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (72, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (73, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (74, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (75, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (76, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (77, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (78, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (79, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (80, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (81, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (82, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (83, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (84, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (85, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (86, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (87, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (88, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (89, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (90, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (91, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (92, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (93, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (94, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (95, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (96, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (97, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (98, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (99, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (100, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (101, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (102, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (103, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (104, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (105, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (106, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (107, 3);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (1, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (2, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (5, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (6, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (9, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (10, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (11, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (12, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (13, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (16, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (18, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (19, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (20, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (21, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (22, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (23, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (24, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (25, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (26, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (28, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (29, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (30, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (31, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (36, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (38, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (40, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (41, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (42, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (44, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (45, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (46, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (47, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (48, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (49, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (52, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (53, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (55, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (56, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (57, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (58, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (60, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (61, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (62, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (63, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (64, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (65, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (66, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (67, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (69, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (70, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (71, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (72, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (77, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (78, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (79, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (80, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (82, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (83, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (84, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (85, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (86, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (87, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (88, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (89, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (90, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (91, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (94, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (95, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (97, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (99, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (100, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (101, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (102, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (104, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (106, 4);
Insert into M9PERMISSIONASSIGNMENT
   (PERMISSION_ID, P_ID)
 Values
   (107, 4);
COMMIT;


