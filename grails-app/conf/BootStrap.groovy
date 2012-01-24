import seclibrary.security.Authority
import seclibrary.security.Person
import seclibrary.security.PersonAuthority
import seclibrary.security.Requestmap

class BootStrap {
	
	private failSave(domain) {
		domain.save(flush: true, failOnError: true)
	}

    def init = { servletContext ->

		def adminRole = failSave (new Authority(authority: 'ROLE_ADMIN'))
		def bookViewRole = failSave (new Authority(authority: 'ROLE_BOOK_VIEW'))
		def bookCreateRole = failSave (new Authority(authority: 'ROLE_BOOK_CREATE'))
//		def bookEditRole = failSave (new Authority(authority: 'ROLE_BOOK_EDIT'))
//		def bookDeleteRole = failSave (new Authority(authority: 'ROLE_BOOK_DELETE'))
		def bookAllRole = failSave (new Authority(authority: 'ROLE_BOOK_ALL'))
		
		def adminUser = failSave(new Person(username: 'admin', password: 'admin', enabled: 'true'))
		def viewUser = failSave(new Person(username: 'view', password: 'view', enabled: 'true'))
		def createUser = failSave(new Person(username: 'create', password: 'create', enabled: 'true'))
//		def editUser = failSave(new Person(username: 'edit', password: 'edit', enabled: 'true'))
//		def deleteUser = failSave(new Person(username: 'delete', password: 'delete', enabled: 'true'))
		def allUser = failSave(new Person(username: 'all', password: 'all', enabled: 'true'))
		
		
		PersonAuthority.create adminUser, adminRole, true
		PersonAuthority.create viewUser, bookViewRole, true
		PersonAuthority.create createUser, bookCreateRole, true
//		PersonAuthority.create editUser, bookEditRole, true
//		PersonAuthority.create deleteUser, bookDeleteRole, true
		PersonAuthority.create allUser, bookAllRole, true
		
		failSave (new Requestmap(url:'/', configAttribute: 'permitAll'))
		failSave (new Requestmap(url:'/plugins/**', configAttribute: 'permitAll'))
		failSave (new Requestmap(url:'/login/**', configAttribute:'permitAll'))
		failSave (new Requestmap(url:'/logout/**', configAttribute:'permitAll'))
		failSave (new Requestmap(url:'/static/**', configAttribute:'permitAll'))
		failSave (new Requestmap(url:'/images/**', configAttribute:'permitAll'))
		failSave (new Requestmap(url:'/css/**', configAttribute:'permitAll'))
		failSave (new Requestmap(url:'/js/**', configAttribute:'permitAll'))
		failSave (new Requestmap(url:'/**', configAttribute:"hasAnyRole('ROLE_ADMIN')"))
		
		failSave (new Requestmap(url:'/book', configAttribute:"hasAnyRole('ROLE_BOOK_VIEW')"))
		failSave (new Requestmap(url:'/book/', configAttribute:"hasAnyRole('ROLE_BOOK_VIEW')"))
		failSave (new Requestmap(url:'/book/index', configAttribute:"hasAnyRole('ROLE_BOOK_VIEW')"))
		failSave (new Requestmap(url:'/book/list', configAttribute:"hasAnyRole('ROLE_BOOK_VIEW')"))
		failSave (new Requestmap(url:'/book/show/*', configAttribute:"hasAnyRole('ROLE_BOOK_VIEW')"))
		failSave (new Requestmap(url:'/book/create', configAttribute:"hasAnyRole('ROLE_BOOK_CREATE')"))
		failSave (new Requestmap(url:'/book/save', configAttribute:"hasAnyRole('ROLE_BOOK_CREATE')"))
		failSave (new Requestmap(url:'/book/edit/*', configAttribute:"hasAnyRole('ROLE_BOOK_CREATE')"))
    }
    def destroy = {
    }
}
