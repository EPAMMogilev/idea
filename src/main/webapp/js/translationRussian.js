(function() {
     angular
         .module('ideaApp')
         .config(config);

     config.$inject = ['$translateProvider'];
     function config($translateProvider) {
         $translateProvider.translations('ru', {
             OR:'или',
             USERNAME: 'Имя прользователя',
             PASSWORD: 'Пароль',
             FIND: 'Найти',
             ADD:'Добавить',
             UPDATE:'Обновить',
             CHANGE:'Изменить',
             DELETE:'Удалить',
             BACK: 'Назад',
             COMMENTS: 'Комментарии',
             IDEA:'Идея',
             DESCRIPTION:'Описание',
             CATEGORY:'Категория',
             CREATED:'Создана',
             UPLOAD_FILE:'Загрузить файл',
             DRAG_FILE:'Перетянуть файл',
             ENTER_USERNAME: 'Введите имя пользователя',
             ENTER_PASSWORD:'Введите пароль',
             ENTER_EMAIL:'Введите email',
             CONFIRM_PASSWORD:'Подтвердить пароль',
             PASSWORD_DOES_NOT_MUCH:'Пароль не совпадает',
             ENTER:'Вход',
             LOGIN:'Войти',
             LOGOUT:'Выйти',
             SIGN_UP:'Зарегистрироваться',
             REGISTRATION:'Регистрация',
             I_HAVE_IDEA:'У меня есть идея!',
             SIGN_IN_WITH:'Войти через',
             ADD_IDEA:'Добавить идею',
             PROFILE:'Профиль',
             MY_IDEA:'Мои идея',
             MY_IDEAS:'Мои идеи',
             WRONG_EMAIL_PASSWORD:'Неверный email и/или пароль',
             USERNAME_IS_TOO_LONG:'Имя пользователя слишком длинное',
             EMAIL_IS_TOO_LONG:'E-mail пользователя слишком длинный',
             ENTER_CORRECT_EMAIL:'Введите корректный e-mail',
             EMAIL_ALREADY_USED:'Данный e-mail занят другим пользователем',
             PASSWORD_IS_TOO_LONG:'Пароль пользователя слишком длинный',
             PASSWORD_IS_TOO_SHORT:'Пароль пользователя слишком короткий',
             THIS_IDEA_HAS_LIKED:'Кому понравилась эта идея',
             YOUR_SUGGESTION:"Ваше предложение",
             BUTTON_LANG_EN: 'en',
             BUTTON_LANG_RU: 'ru'
         });
         $translateProvider.useSanitizeValueStrategy('sanitize');
         $translateProvider.preferredLanguage('ru');
     }
})();

