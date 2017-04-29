(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('home', {
            parent: 'app',
            url: '/',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/components/login-quenti/login-quenti.html',
                    controller: 'LoginQuentiController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('login');
                    return $translate.refresh();
                }]
            }
        })
        .state('home-quenti', {
            parent: 'app',
            url: '/index',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/home-quenti.html',
                    controller: "HomeQuentiController",
                    controllerAs: "vm"
                }
            },
            resolve: {
                pruebas: ['Prueba', function(Prueba){
                    return Prueba.query().$promise.then(function(data){
                        data.forEach(function(prueba){
                            prueba.pruebaNombre = prueba.ambienteNombre + ' / ' + prueba.metodoNombre + ' / ' + prueba.moduloNombre;
                        });
                        return data;
                    });
                }],
                sesion: ['Principal', 'Seguridad', 'User', function(Principal, Seguridad, User){
                   return Principal.identity().then(function(account){
                      return User.get({login: account.login}).$promise.then(function(user){
                          return Seguridad.getByUserId({id: user.id}).$promise.then(function(seguridad){
                              return {
                                  token: seguridad.token,
                                  userId: user.id,
                                  userName: user.firstName
                              };
                          });
                      })
                   });
                }],
                serviceProviders: ['ServiceProvider', function(ServiceProvider){
                   return ServiceProvider.query();
                }],
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        })
        .state('home-admin', {
            parent: 'app',
            url: '/main',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/home.html'
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
