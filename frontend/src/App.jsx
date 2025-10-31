import { useState } from 'react'
import { useAuth } from './context/AuthContext'
import './App.css'

function App() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const { login, isAuthenticated, logout } = useAuth()

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setLoading(true)

    const result = await login(email, password)

    if (result.success) {
      console.log('Login effettuato con successo!')
    } else {
      setError(result.error || 'Errore durante il login')
    }

    setLoading(false)
  }

  const handleLogout = () => {
    logout()
    setEmail('')
    setPassword('')
    setError('')
  }

  if (isAuthenticated) {
    return (
      <div className="login-container">
        <div className="login-card">
          <h1>Energy Management System</h1>
          <h2>Benvenuto!</h2>
          <p>Sei autenticato correttamente.</p>
          <button onClick={handleLogout} className="login-button">
            Esci
          </button>
        </div>
      </div>
    )
  }

  return (
    <div className="login-container">
      <div className="login-card">
        <h1>Energy Management System</h1>
        <h2>Login</h2>

        {error && <div className="error-message">{error}</div>}

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Inserisci la tua email"
              required
              disabled={loading}
            />
          </div>

          <div className="form-group">
            <label htmlFor="password">Password</label>
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Inserisci la tua password"
              required
              disabled={loading}
            />
          </div>

          <button type="submit" className="login-button" disabled={loading}>
            {loading ? 'Accesso in corso...' : 'Accedi'}
          </button>
        </form>
      </div>
    </div>
  )
}

export default App
