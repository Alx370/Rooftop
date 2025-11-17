import { useState, useEffect } from 'react';
import { chiSiamoService } from '../../services/chiSiamoService';
import './chisiamo.css';

const ChiSiamo = () => {
  const [chiSiamoData, setChiSiamoData] = useState(null);
  const [teamData, setTeamData] = useState([]);
  const [missioneData, setMissioneData] = useState(null);
  const [valoriData, setValoriData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        const [chiSiamo, team, missione, valori] = await Promise.all([
          chiSiamoService.getChiSiamo().catch(() => null),
          chiSiamoService.getTeam().catch(() => []),
          chiSiamoService.getMissione().catch(() => null),
          chiSiamoService.getValori().catch(() => []),
        ]);

        setChiSiamoData(chiSiamo);
        setTeamData(team || []);
        setMissioneData(missione);
        setValoriData(valori || []);
      } catch (err) {
        console.error('Errore nel caricamento dati:', err);
        setError('Errore nel caricamento dei dati');
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  if (loading) {
    return <div className="chi-siamo-container"><p>Caricamento in corso...</p></div>;
  }

  if (error) {
    return <div className="chi-siamo-container"><p className="error">{error}</p></div>;
  }

  return (
    <div className="chi-siamo-container">
      <h1 className="text-3xl font-semibold mb-8">Chi Siamo</h1>

      {/* Sezione Chi Siamo */}
      {chiSiamoData && (
        <section className="chi-siamo-section">
          <h2 className="text-2xl font-semibold mb-4">{chiSiamoData.titolo || 'La Nostra Storia'}</h2>
          <p className="text-lg">{chiSiamoData.descrizione || 'Scopri la nostra missione e visione'}</p>
        </section>
      )}

      {/* Sezione Missione */}
      {missioneData && (
        <section className="missione-section">
          <h2 className="text-2xl font-semibold mb-4">{missioneData.titolo || 'La Nostra Missione'}</h2>
          <p className="text-lg">{missioneData.descrizione || ''}</p>
          {missioneData.visione && (
            <div className="mt-6">
              <h3 className="text-xl font-semibold mb-2">Visione</h3>
              <p className="text-lg">{missioneData.visione}</p>
            </div>
          )}
        </section>
      )}

      {/* Sezione Valori */}
      {valoriData && valoriData.length > 0 && (
        <section className="valori-section">
          <h2 className="text-2xl font-semibold mb-6">I Nostri Valori</h2>
          <div className="valori-grid">
            {valoriData.map((valore) => (
              <div key={valore.id} className="valore-card">
                <h3 className="text-xl font-semibold mb-2">{valore.titolo}</h3>
                <p>{valore.descrizione}</p>
              </div>
            ))}
          </div>
        </section>
      )}

      {/* Sezione Team */}
      {teamData && teamData.length > 0 && (
        <section className="team-section">
          <h2 className="text-2xl font-semibold mb-6">Il Nostro Team</h2>
          <div className="team-grid">
            {teamData.map((membro) => (
              <div key={membro.id} className="team-card">
                {membro.foto && <img src={membro.foto} alt={membro.nome} className="team-photo" />}
                <h3 className="text-xl font-semibold mb-1">{membro.nome}</h3>
                <p className="text-sm text-gray-600 mb-2">{membro.ruolo}</p>
                <p>{membro.biografia}</p>
              </div>
            ))}
          </div>
        </section>
      )}
    </div>
  );
};

export default ChiSiamo;
