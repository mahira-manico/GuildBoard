import { useEffect, useState } from 'react';
import { adventurerService } from '../services/adventurerService';
import type { AdventurerAnswer } from '../types/adventurer';
import type { AssignmentAnswer } from '../types/assignment'; 

export const AdventurerPage = () => {
  const [adventurer, setAdventurer] = useState<AdventurerAnswer[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedAdventurer, setSelectedAdventurer] = useState<AdventurerAnswer | null>(null);
  const [adventurerHistory, setAdventurerHistory] = useState<AssignmentAnswer[]>([]);

  const fetchAdventurers = async () => {
    try {
      setLoading(true);
      setError(null);
      const answer = await adventurerService.getAllAdventurers();
      setAdventurer(answer);
    } catch (err: unknown) {
      if (err instanceof Error) {
        setError(err.message);
      } else {
        setError('Une erreur inattendue est survenue');
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAdventurers();
  }, []);

  const handleSelectAdventurer = async (id: number) => {
    try {
      setLoading(true);
      setError(null);

      const [advData, historyData] = await Promise.all([
        adventurerService.getAdventurerById(id),
        adventurerService.seeHistory(id),
      ]);

      setSelectedAdventurer(advData);
      setAdventurerHistory(historyData);
    } catch (err: unknown) {
      if (err instanceof Error) {
        setError(err.message);
      } else {
        setError('Impossible de charger les informations de cet aventurier.');
      }
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteAdventurer = async (id: number) => {
    try {
      setLoading(true);
      setError(null);

      await adventurerService.deleteAdventurer(id);
      setAdventurer((prevAdv) => prevAdv.filter((a) => a.id !== id));
      setSelectedAdventurer(null);
      setAdventurerHistory([]);
    } catch (err: unknown) {
      if (err instanceof Error) {
        setError(err.message);
      } else {
        setError('Une erreur inattendue est survenue lors de la suppression');
      }
    } finally {
      setLoading(false);
    }
  };

  const maxExp = selectedAdventurer ? selectedAdventurer.level * 100 : 100;
  const xpPercentage = selectedAdventurer
    ? Math.min(100, Math.round((selectedAdventurer.xp / maxExp) * 100))
    : 0;

  return (
    <div className="page-container" style={{ margin: '1rem 0' }}>
      <div className="header">
        <h1>GUILDBOARD</h1>

        <div className="error">
          {loading && <p>Chargement en cours...</p>}
          {error && <p style={{ color: 'red' }}>Erreur : {error}</p>}
        </div>

        <div className="SideNameCards" style={{ display: 'flex', gap: '0.5rem' }}>
          <button type="button" className="btn-action">Quêtes</button>
          <button type="button" className="btn-action active">Aventuriers</button>
        </div>
      </div>

      <div style={{ display: 'flex', gap: '2rem', marginTop: '1.5rem' }}>
        
        <div className="listSide" style={{ flex: 1 }}>
          <div className="detailCard">
            <h2>Membres</h2>
          </div>

          {!loading && !error && adventurer.length === 0 && (
            <p>Aucun aventurier existant</p>
          )}

          <div className="adventurerCards">
            <ul style={{ listStyle: 'none', padding: 0 }}>
              {adventurer.map((a) => (
                <li
                  key={a.id}
                  onClick={() => handleSelectAdventurer(a.id)}
                  style={{
                    padding: '8px',
                    margin: '6px 0',
                    cursor: 'pointer',
                    borderRadius: '4px',
                    backgroundColor: selectedAdventurer?.id === a.id ? '#dcd1c4' : '#eee3d3',
                    display: 'flex',
                    justifyContent: 'space-between',
                  }}
                >
                  <strong>{a.name}</strong> — {a.characterClass}
                  <span>lv.{a.level}</span>
                </li>
              ))}
            </ul>
          </div>

          <div style={{ display: 'flex', gap: '8px', marginTop: '1rem' }}>
            <button type="button" className="btn-action">+</button>
            <button
              type="button"
              className="btn-action"
              disabled={!selectedAdventurer}
              onClick={() => selectedAdventurer && handleDeleteAdventurer(selectedAdventurer.id)}
            >
              -
            </button>
            <button type="button" className="btn-action" disabled={!selectedAdventurer}>
              Modifier
            </button>
          </div>
        </div>

        <div className="profileSide" style={{ flex: 1 }}>
          {selectedAdventurer ? (
            <div className="profileContent">
              <div className="detailCard">
                <h2>Détails</h2>
              </div>

              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <h3>— {selectedAdventurer.name} — {selectedAdventurer.characterClass}</h3>
                <div className="roundLevel">
                  <span>lv.</span>{selectedAdventurer.level}
                </div>
              </div>

              <div style={{ margin: '1rem 0' }}>
                <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.85rem' }}>
                  <span>Progression XP</span>
                  <span>{selectedAdventurer.xp} / {maxExp} XP ({xpPercentage}%)</span>
                </div>
                <div style={{ width: '100%', height: '14px', background: '#4a3b32', borderRadius: '8px', overflow: 'hidden' }}>
                  <div
                    style={{
                      width: `${xpPercentage}%`,
                      height: '100%',
                      background: '#2ecc71',
                      transition: 'width 0.3s ease',
                    }}
                  />
                </div>
              </div>

              <p>Or total : {selectedAdventurer.gold}</p>
              <div className="questContent" style={{ marginTop: '1.5rem' }}>
                <div className="detailCard">
                  <h3>Quêtes</h3>
                </div>

                <div className="questCards">
                  {adventurerHistory.length === 0 ? (
                    <p>Aucune quête pour le moment.</p>
                  ) : (
                    adventurerHistory.map((item) => (
                      <div
                        key={item.id}
                        style={{
                          border: '1px solid #c8b7a6',
                          borderRadius: '6px',
                          padding: '0.5rem',
                          margin: '0.5rem 0',
                          display: 'flex',
                          justifyContent: 'space-between',
                          alignItems: 'center',
                        }}
                      >
                        <div>
                          <strong>{item.quest.title}</strong>
                          <p style={{ margin: '4px 0', fontSize: '0.85rem' }}>
                            Statut : {item.completed_at ? `Terminée le ${new Date(item.completed_at).toLocaleDateString()}` : 'En cours'}
                          </p>
                        </div>

                        <span>lv.{item.quest.requiredLevel}</span>
                      </div>
                    ))
                  )}
                </div>
              </div>
            </div>
          ) : (
            <p>Sélectionne un membre dans la liste pour consulter sa fiche.</p>
          )}
        </div>

      </div>
    </div>
  );
};