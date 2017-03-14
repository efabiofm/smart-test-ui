(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('parametro', {
            parent: 'entity',
            url: '/parametro',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.parametro.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/parametro/parametros.html',
                    controller: 'ParametroController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('parametro');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('parametro-detail', {
            parent: 'entity',
            url: '/parametro/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.parametro.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/parametro/parametro-detail.html',
                    controller: 'ParametroDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('parametro');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Parametro', function($stateParams, Parametro) {
                    return Parametro.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'parametro',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('parametro-detail.edit', {
            parent: 'parametro-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parametro/parametro-dialog.html',
                    controller: 'ParametroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Parametro', function(Parametro) {
                            return Parametro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('parametro.new', {
            parent: 'parametro',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parametro/parametro-dialog.html',
                    controller: 'ParametroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('parametro', null, { reload: 'parametro' });
                }, function() {
                    $state.go('parametro');
                });
            }]
        })
        .state('parametro.edit', {
            parent: 'parametro',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parametro/parametro-dialog.html',
                    controller: 'ParametroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Parametro', function(Parametro) {
                            return Parametro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('parametro', null, { reload: 'parametro' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('parametro.delete', {
            parent: 'parametro',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parametro/parametro-delete-dialog.html',
                    controller: 'ParametroDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Parametro', function(Parametro) {
                            return Parametro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('parametro', null, { reload: 'parametro' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
