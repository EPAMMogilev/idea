(function() {
     angular
         .module('ideaApp')
         .config(config);

     config.$inject = ['$translateProvider'];
     function config($translateProvider) {
         $translateProvider.translations('en', {
             OR:"or",
             USERNAME: 'Username',
             PASSWORD: 'Password',
             FIND: 'Find',
             ADD:'Add',
             UPDATE:'Update',
             CHANGE:'Change',
             DELETE:'Delete',
             BACK: 'Back',
             COMMENTS: 'Comments',
             IDEA:'Idea',
             DESCRIPTION:'Description',
             CATEGORY:'Category',
             CREATED:'Created',
             UPLOAD_FILE:'Upload File',
             DRAG_FILE:'Drag File',
             ENTER_USERNAME: 'Enter your username',
             ENTER_PASSWORD:'Enter your password',
             ENTER_EMAIL:'Enter your email',
             CONFIRM_PASSWORD:'Confirm your password',
             PASSWORD_DOES_NOT_MUCH:'Password does not much',
             ENTER:'Enter',
             LOGIN:'Login',
             LOGOUT:'Logout',
             SIGN_UP:'Sign up',
             REGISTRATION:'Registration',
             I_HAVE_IDEA:'I Have Idea!',
             SIGN_IN_WITH:'Sign in with',
             ADD_IDEA:'Add Idea',
             PROFILE:'Profile',
             MY_IDEA:'My idea',
             MY_IDEAS:'My ideas',
             WRONG_EMAIL_PASSWORD:'Wrong email or password',
             USERNAME_IS_TOO_LONG:'Username is too long',
             EMAIL_IS_TOO_LONG:'Email is too long',
             ENTER_CORRECT_EMAIL:'Enter correct email',
             EMAIL_ALREADY_USED:'This email use by another user',
             PASSWORD_IS_TOO_LONG:'Password is too long',
             PASSWORD_IS_TOO_SHORT:'Password is too short',
             THIS_IDEA_HAS_LIKED:'This idea has liked',
             YOUR_SUGGESTION:"Your suggestion",
             BUTTON_LANG_EN: 'en',
             BUTTON_LANG_RU: 'ru'
         });
     }
})();

